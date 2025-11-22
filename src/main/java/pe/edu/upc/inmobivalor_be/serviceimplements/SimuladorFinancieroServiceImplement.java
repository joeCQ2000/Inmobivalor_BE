package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.dtos.*;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ISimuladorFinancieroService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimuladorFinancieroServiceImplement implements ISimuladorFinancieroService {

    /**
     * SIMULACIÓN FRANCÉS (UN SOLO PLAN DE PAGO)
     */
    @Override
    public SimulacionFrancesResponseDTO simularFrances(DatosCronogramaDTO datos) {
        return buildSimulacionFrances(datos);
    }

    /**
     * SIMULACIÓN FRANCÉS PARA VARIOS PLANES DE PAGO
     * (puedes llamar este método desde el Controller para recibir un JSON con un array de planes).
     */
    @Override
    public List<SimulacionFrancesResponseDTO> simularFrancesBatch(List<DatosCronogramaDTO> listaDatos) {
        List<SimulacionFrancesResponseDTO> resultados = new ArrayList<>();
        if (listaDatos == null || listaDatos.isEmpty()) {
            return resultados;
        }

        for (DatosCronogramaDTO datos : listaDatos) {
            resultados.add(buildSimulacionFrances(datos));
        }
        return resultados;
    }

    /**
     * Lógica central de cálculo (la que tenías en simularFrances),
     * aislada en un método privado reutilizable.
     */
    private SimulacionFrancesResponseDTO buildSimulacionFrances(DatosCronogramaDTO datos) {

        // ===== 1. LECTURA DE ENTRADAS =====
        double precio = datos.getPrecio_venta_activo();           // D4
        double pctCuotaInicial = datos.getPorcentaje_cuota_inicial(); // D5 (0.2 por 20%)
        int numeroAnhos = datos.getNumero_anhos();                // D6
        double cuotasPorAnho = datos.getFrecuencia_pago();        // N° cuotas por año (4, 12, etc.)
        double diasPorAnho = datos.getNumero_dias_por_anho();     // 360
        double diasPorPeriodo = diasPorAnho / cuotasPorAnho;      // p.e. 90 si es trimestral

        // Costos iniciales
        double costesNotariales    = datos.getCostes_notariales();
        double costesRegistrales   = datos.getCostes_registrales();
        double tasacion            = datos.getTasacion();
        double comisionEstudio     = datos.getComision_estudio();
        double comisionActivacion  = datos.getComision_activacion();

        // Costos periódicos
        double comisionPeriodica   = datos.getComision_periodica();
        double portes              = datos.getPortes();
        double gastosAdm           = datos.getGastos_administracion();

        // Seguros
        double pctSegDesMensual    = datos.getPorcentaje_seguro_desgravamen(); // ej. 0.00045
        double pctSegRiesgoAnual   = datos.getPorcentaje_seguro_riesgo();      // ej. 0.004

        // Tasas
        double tasaDescuentoAnual  = datos.getTasa_descuento();   // TEA de descuento
        double tea                 = datos.getTea();              // TEA del préstamo

        // Gracia
        int mesesGracia            = datos.getMeses_gracia();
        String tipoGracia          = datos.getTipo_gracia();      // "SIN", "TOTAL", "PARCIAL"

        // ===== 2. DERIVADOS BÁSICOS =====

        // Saldo a financiar y monto del préstamo
        double saldoFinanciar = precio * (1.0 - pctCuotaInicial);
        double montoPrestamo  = saldoFinanciar
                + costesNotariales
                + costesRegistrales
                + tasacion
                + comisionEstudio
                + comisionActivacion;

        int nCuotasPorAnho = (int) Math.round(cuotasPorAnho);
        int nTotalCuotas   = nCuotasPorAnho * numeroAnhos;

        datos.setSaldo_a_financiar(saldoFinanciar);
        datos.setMonto_prestamo(montoPrestamo);
        datos.setNumero_cuotas_por_anho(nCuotasPorAnho);
        datos.setNumero_total_cuotas(nTotalCuotas);

        // Tasa periódica efectiva del préstamo
        double tasaPeriodica = Math.pow(1.0 + tea, diasPorPeriodo / diasPorAnho) - 1.0;

        // % seguro desgravamen por período
        double pctSegDesPeriodo = pctSegDesMensual / 30.0 * diasPorPeriodo;

        // Seguro de riesgo por período
        double seguroRiesgoPeriodo = pctSegRiesgoAnual * precio / nCuotasPorAnho;

        // Tasa de descuento periódica (para VAN)
        double tasaDescPeriodo = Math.pow(1.0 + tasaDescuentoAnual, diasPorPeriodo / diasPorAnho) - 1.0;

        // ===== 3. SALDO DESPUÉS DE GRACIA PARA OBTENER CUOTA FRANCESA =====

        double saldoTmp = montoPrestamo;

        for (int t = 1; t <= mesesGracia && t <= nTotalCuotas; t++) {
            double interes = saldoTmp * tasaPeriodica;

            if ("TOTAL".equalsIgnoreCase(tipoGracia)) {
                // Se capitalizan los intereses
                saldoTmp += interes;
            } else if ("PARCIAL".equalsIgnoreCase(tipoGracia)) {
                // Solo se paga el interés, el saldo no cambia
            } else {
                // SIN gracia -> se rompe el bucle de gracia
                break;
            }
        }

        int cuotasRestantes = nTotalCuotas - Math.min(mesesGracia, nTotalCuotas);
        double cuotaBase = 0.0; // cuota "pura" (sin seguro desgravamen)
        if (cuotasRestantes > 0) {
            cuotaBase = frenchPayment(saldoTmp, tasaPeriodica, cuotasRestantes);
        }

        // ===== 4. CONSTRUIR CRONOGRAMA =====

        List<CronogramaDTO> cronograma = new ArrayList<>();
        double saldo = montoPrestamo;

        double totalIntereses = 0.0;
        double totalAmortizacion = 0.0;
        double totalSegDes = 0.0;
        double totalSegRiesgo = 0.0;
        double totalComisiones = 0.0;
        double totalPortesYGastos = 0.0;

        double[] flujos = new double[nTotalCuotas + 1];
        flujos[0] = montoPrestamo; // Entrada del préstamo en t=0

        for (int t = 1; t <= nTotalCuotas; t++) {
            CronogramaDTO c = new CronogramaDTO();

            c.setNumero_cuota(t);
            c.setTea(tea);
            c.setTasa_periodica(tasaPeriodica);
            c.setIa(0.0);
            c.setIp(0.0);

            String pg;
            if (t <= mesesGracia && !"SIN".equalsIgnoreCase(tipoGracia)) {
                pg = "TOTAL".equalsIgnoreCase(tipoGracia) ? "T" : "P";
            } else {
                pg = "S";
            }
            c.setPeriodo_gracia(pg);

            double saldoInicial = saldo;
            double saldoIndexado = saldoInicial; // sin indexación
            double interes = saldoInicial * tasaPeriodica;

            double segDes = saldoIndexado * pctSegDesPeriodo;
            double segRiesgo = seguroRiesgoPeriodo;
            double comisionPer = comisionPeriodica;
            double portesPer = portes;
            double gastosPer = gastosAdm;

            double amort = 0.0;
            double cuotaIncSegDes = 0.0; // "Cuota (inc Seg Des)"

            if ("T".equals(pg)) {
                // Gracia total: no se paga cuota ni amortiza, se capitaliza interés
                amort = 0.0;
                cuotaIncSegDes = 0.0;
                saldo = saldoInicial + interes;
            } else if ("P".equals(pg)) {
                // Gracia parcial: solo se paga interés
                amort = 0.0;
                cuotaIncSegDes = interes; // solo interés
                saldo = saldoInicial;
            } else {
                // Sin gracia: cuota francesa (constante) a partir de aquí
                double cuotaPura = cuotaBase;    // capital + interés
                amort = cuotaPura - interes;     // parte que amortiza capital
                cuotaIncSegDes = cuotaPura + segDes;
                saldo = saldoInicial - amort;
            }

            // Acumulados (valores positivos)
            totalIntereses += interes;
            totalAmortizacion += amort;
            totalSegDes += segDes;
            totalSegRiesgo += segRiesgo;
            totalComisiones += comisionPer;
            totalPortesYGastos += (portesPer + gastosPer);

            // Flujo de caja del período (salida => negativo para el cliente)
            double pagoPeriodo;
            if ("T".equals(pg)) {
                // En gracia total: solo seguros + comisiones + portes + gastos
                pagoPeriodo = segDes + segRiesgo + comisionPer + portesPer + gastosPer;
            } else if ("P".equals(pg)) {
                // en P: interés (cuotaIncSegDes) + seguros y comisiones
                pagoPeriodo = cuotaIncSegDes + segDes + segRiesgo + comisionPer + portesPer + gastosPer;
            } else {
                // en S: cuota (inc Seg Des) + seguro riesgo + comisiones
                pagoPeriodo = cuotaIncSegDes + segRiesgo + comisionPer + portesPer + gastosPer;
            }
            double flujo = -pagoPeriodo;  // salida de dinero
            flujos[t] = flujo;

            // Llenado del DTO con signo "contable" (egresos en negativo)
            c.setSaldo_inicial(saldoInicial);
            c.setSaldo_inicial_indexado(saldoIndexado);
            c.setInteres(-interes);
            c.setCuota_incluye_seguro_desgravamen(-cuotaIncSegDes);
            c.setAmortizacion(-amort);
            c.setPrepago(0.0); // no manejamos prepago aún
            c.setSeguro_desgravamen(-segDes);
            c.setSeguro_riesgo(-segRiesgo);
            c.setComision(-comisionPer);
            c.setPortes(-portesPer);
            c.setGastos_administracion(-gastosPer);
            c.setSaldo_final(saldo);
            c.setFlujo(flujo);

            cronograma.add(c);
        }

        // ===== 5. TOTALES E INDICADORES =====

        datos.setTotal_intereses(totalIntereses);
        datos.setTotal_amortizacion_capital(totalAmortizacion);
        datos.setTotal_seguro_desgravamen(totalSegDes);
        datos.setTotal_seguro_riesgo(totalSegRiesgo);
        datos.setTotal_comisiones_periodicas(totalComisiones);
        datos.setTotal_portes_y_gastos_adm(totalPortesYGastos);

        IndicadoresFinancierosDTO ind = new IndicadoresFinancierosDTO();

        // VAN con tasa de descuento periódica
        double van = npv(tasaDescPeriodo, flujos);
        ind.setVan(van);

        // IRR periódica y TCEA
        double tirPeriodo = irr(flujos);
        ind.setTir(tirPeriodo);

        double tcea = Math.pow(1.0 + tirPeriodo, nCuotasPorAnho) - 1.0;
        ind.setTcea((int) Math.round(tcea * 100)); // en %

        // ===== 6. RESPUESTA =====
        SimulacionFrancesResponseDTO resp = new SimulacionFrancesResponseDTO();
        resp.setDatos(datos);
        resp.setCronograma(cronograma);
        resp.setIndicadores(ind);

        return resp;
    }

    // ===== UTILIDADES FINANCIERAS =====

    // Cuota francesa estándar
    private double frenchPayment(double principal, double rate, int n) {
        if (n <= 0) return 0.0;
        if (Math.abs(rate) < 1e-12) {
            return principal / n;
        }
        return principal * rate / (1.0 - Math.pow(1.0 + rate, -n));
    }

    // NPV con tasa periódica
    private double npv(double rate, double[] cashFlows) {
        double npv = 0.0;
        for (int t = 0; t < cashFlows.length; t++) {
            npv += cashFlows[t] / Math.pow(1.0 + rate, t);
        }
        return npv;
    }

    // IRR usando búsqueda binaria simple
    private double irr(double[] cashFlows) {
        double low = -0.9999;
        double high = 10.0;
        double mid = 0.0;

        for (int i = 0; i < 100; i++) {
            mid = (low + high) / 2.0;
            double value = npv(mid, cashFlows);

            if (value > 0) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return mid;
    }
}
