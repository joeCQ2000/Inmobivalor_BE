package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.dtos.CronogramaDTO;
import pe.edu.upc.inmobivalor_be.dtos.DatosCronogramaDTO;
import pe.edu.upc.inmobivalor_be.dtos.SimulacionFrancesResponseDTO;
import pe.edu.upc.inmobivalor_be.entities.CreditoPrestamo;
import pe.edu.upc.inmobivalor_be.entities.Entidad_tasa;
import pe.edu.upc.inmobivalor_be.repositories.ICreditoPrestamoRepository;
import pe.edu.upc.inmobivalor_be.repositories.IEntidad_tasaRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ISimuladorFinancieroService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimuladorFinancieroServiceImplement implements ISimuladorFinancieroService {

    @Autowired
    private IEntidad_tasaRepository entidadTasaRepository;

    @Autowired
    private ICreditoPrestamoRepository creditoPrestamoRepository;

    @Override
    public SimulacionFrancesResponseDTO simularFrances(DatosCronogramaDTO datos) {

        // ========= 0. CRÉDITO: traer datos desde CreditoPrestamo =========
        CreditoPrestamo credito = creditoPrestamoRepository
                .findById(datos.getIdCredito())
                .orElseThrow(() ->
                        new RuntimeException("No se encontró el crédito con el id: " + datos.getIdCredito()));

        String capitalizacion = credito.getCapitalizacion();  // diaria, semanal, mensual, anual...
        String tipoGracia = credito.getTipo_gracia();         // total, parcial, ninguno...
        int mesesGracia = credito.getMeses_gracia();          // Nº meses de gracia

        int plazoMeses;
        try {
            plazoMeses = Integer.parseInt(credito.getPlazo_meses().trim());
        } catch (NumberFormatException e) {
            throw new RuntimeException("El campo plazo_meses del crédito debe ser numérico (en meses). Valor actual: "
                    + credito.getPlazo_meses());
        }

        // Convertimos plazo en años (redondeando hacia arriba si no es múltiplo de 12)
        int numeroAnhos = plazoMeses / 12;
        if (plazoMeses % 12 != 0) {
            numeroAnhos++;
        }

        // Impactamos estos valores en el DTO para que también viajen al front
        datos.setNumero_anhos(numeroAnhos);
        datos.setTipo_gracia(tipoGracia);
        datos.setMeses_gracia(mesesGracia);

        // ========= 1. DATOS INGRESADOS =========
        double precio = datos.getPrecio_venta_activo();               // Precio Venta Activo
        double pctCuotaInicial = datos.getPorcentaje_cuota_inicial(); // % Cuota Inicial (ej. 0.20)
        int diasPorAnho = datos.getNumero_dias_por_anho();            // Nº días por año (típico 360)

        double costesNotariales = datos.getCostes_notariales();
        double costesRegistrales = datos.getCostes_registrales();
        double tasacion = datos.getTasacion();
        double comisionEstudio = datos.getComision_estudio();
        double comisionActivacion = datos.getComision_activacion();

        double comisionPeriodica = datos.getComision_periodica();
        double portes = datos.getPortes();
        double gastosAdmin = datos.getGastos_administracion();

        double pctSegDesMensual = datos.getPorcentaje_seguro_desgravamen(); // ej. 0.00045
        double pctSegRiesgoAnual = datos.getPorcentaje_seguro_riesgo();     // ej. 0.004 (0.40%)
        double tasaDescuentoAnual = datos.getTasa_descuento();              // 0.27

        // ========= 2. DERIVADOS BÁSICOS =========
        // Saldo a financiar = PV - PV * %CuotaInicial
        double saldoFinanciar = precio - (precio * pctCuotaInicial);

        // Monto del préstamo = Saldo a financiar + costes iniciales
        double montoPrestamo = saldoFinanciar
                + costesNotariales
                + costesRegistrales
                + tasacion
                + comisionEstudio
                + comisionActivacion;

        datos.setSaldo_a_financiar(saldoFinanciar);
        datos.setMonto_prestamo(montoPrestamo);

        // ========= 3. TASA DESDE BD (Entidad_tasa -> Tasa_interes) =========
        int entidadId = datos.getEntidadId();

        List<Entidad_tasa> entidadTasas = entidadTasaRepository.findActivasByEntidad(entidadId);
        if (entidadTasas.isEmpty()) {
            throw new RuntimeException("No se encontró ninguna tasa activa para la entidad " + entidadId);
        }

        // Por ahora tomamos la primera tasa activa de esa entidad
        Entidad_tasa entidadTasa = entidadTasas.get(0);
        String tipoTasa = entidadTasa.getTasa().getTipo_tasa();   // "TEA", "TNA", "TN", etc.

        // tasa_pct puede venir como 11 (11%) o 0.11; normalizamos a decimal
        double tasaBruta = entidadTasa.getTasa().getTasa_pct();
        double tasaAnual = (tasaBruta > 1.0) ? tasaBruta / 100.0 : tasaBruta; // 11 -> 0.11, 0.11 -> 0.11

        boolean esNominal = tipoTasa != null &&
                (tipoTasa.equalsIgnoreCase("TN")
                        || tipoTasa.equalsIgnoreCase("TNA")
                        || tipoTasa.toUpperCase().startsWith("TN"));

        boolean esEfectiva = tipoTasa != null &&
                (tipoTasa.equalsIgnoreCase("TEA")
                        || tipoTasa.toUpperCase().startsWith("TE"));

        // ========= 3.1 FRECUENCIA DE PAGO =========
        // Regla: capitalización SOLO aplica si la tasa es nominal.
        //        Si la tasa es efectiva, ignorar capitalización y usar frecuenciaPago = 360 por defecto.
        int frecuenciaPago; // días por periodo de pago

        if (esNominal) {
            // Capitalización -> días por periodo
            if ("diaria".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 1;       // 1 día
            } else if ("semanal".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 7;       // 7 días
            } else if ("mensual".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 30;      // 30 días
            } else if ("anual".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 360;     // 360 días
            } else {
                throw new IllegalArgumentException("Capitalización no soportada para tasa nominal: " + capitalizacion);
            }
        } else if (esEfectiva) {
            // Para TEA se ignora la capitalización.
            // Por requisito: si no viene nada, tomar 360 por defecto.
            int frecuenciaDesdeFront = datos.getFrecuencia_pago();
            frecuenciaPago = (frecuenciaDesdeFront > 0) ? frecuenciaDesdeFront : 360;
        } else {
            throw new IllegalArgumentException("Tipo de tasa de interés no soportado: " + tipoTasa);
        }

        // Guardamos en el DTO la frecuencia de pago efectiva usada
        datos.setFrecuencia_pago(frecuenciaPago);

        // Nº cuotas por año = días por año / días por periodo
        int nCuotasPorAnho = diasPorAnho / frecuenciaPago;

        // Nº total de cuotas
        int nTotalCuotas = numeroAnhos * nCuotasPorAnho;

        double diasPorPeriodo = frecuenciaPago;

        datos.setNumero_cuotas_por_anho(nCuotasPorAnho);
        datos.setNumero_total_cuotas(nTotalCuotas);

        // ========= 3.2 CÁLCULO DE TASAS PERIÓDICAS =========
        double tea;
        double tasaPeriodica;

        if (esEfectiva) {
            // TEA conocida -> tasa periódica según días del periodo
            tea = tasaAnual;
            tasaPeriodica = Math.pow(1.0 + tea, diasPorPeriodo / (double) diasPorAnho) - 1.0;
        } else {
            // Tasa nominal anual j: aproximación estándar simple
            double nPeriodosAnho = (double) nCuotasPorAnho;
            tasaPeriodica = tasaAnual / nPeriodosAnho;
            tea = Math.pow(1.0 + tasaPeriodica, nPeriodosAnho) - 1.0;
        }

        // ========= 4. SEGUROS Y DESCUENTO POR PERIODO =========
        // % Seguro desgravamen periodo = %SegDesMensual / 30 * díasPorPeriodo
        double pctSegDesPeriodo = pctSegDesMensual / 30.0 * diasPorPeriodo;

        // Seguro riesgo = %SeguroRiesgo * Precio / NºCuotasPorAño
        double seguroRiesgoPeriodo = pctSegRiesgoAnual * precio / nCuotasPorAnho;

        // (por si luego calculas VAN/TIR)
        double tasaDescPeriodo = Math.pow(1.0 + tasaDescuentoAnual, diasPorPeriodo / (double) diasPorAnho) - 1.0;

        // ========= 5. CRONOGRAMA MÉTODO FRANCÉS =========
        List<CronogramaDTO> cronograma = new ArrayList<>();

        double saldo = montoPrestamo;

        // Acumuladores para totales
        double totalIntereses = 0.0;
        double totalAmort = 0.0;
        double totalSegDes = 0.0;
        double totalSegRiesgo = 0.0;
        double totalComisiones = 0.0;
        double totalPortesGastos = 0.0;

        // 5.1. PERÍODO DE GRACIA TOTAL (capitaliza SOLO intereses)
        int inicioCuotaRegular = 1;
        if ("total".equalsIgnoreCase(tipoGracia) && mesesGracia > 0) {

            for (int i = 1; i <= mesesGracia; i++) {
                CronogramaDTO c = new CronogramaDTO();
                c.setNumero_cuota(i);
                c.setSaldo_inicial(saldo);

                double interes = saldo * tasaPeriodica;
                double seguroDes = pctSegDesPeriodo * saldo;
                double seguroRiesgo = seguroRiesgoPeriodo;

                // En gracia total NO se paga cuota ni amortización;
                // el saldo final = saldo inicial + interes (capitalización)
                double saldoFinal = saldo + interes;

                c.setInteres(interes);
                c.setAmortizacion(0.0);
                c.setCuota_incluye_seguro_desgravamen(0.0);
                c.setSaldo_final(saldoFinal);
                c.setComision(comisionPeriodica);
                c.setPortes(portes);
                c.setGastos_administracion(gastosAdmin);
                c.setSeguro_desgravamen(seguroDes);
                c.setSeguro_riesgo(seguroRiesgo);
                c.setTea(tea);
                c.setTasa_periodica(tasaPeriodica);
                c.setPeriodo_gracia("Total");

                // Acumular totales
                totalIntereses += interes;
                totalSegDes += seguroDes;
                totalSegRiesgo += seguroRiesgo;
                totalComisiones += comisionPeriodica;
                totalPortesGastos += (portes + gastosAdmin);

                cronograma.add(c);

                // actualizar saldo para el siguiente periodo de gracia
                saldo = saldoFinal;
            }

            // Después de gracia total, las cuotas francesas se calculan
            // con el saldo YA capitalizado y sólo con las cuotas restantes
            inicioCuotaRegular = mesesGracia + 1;
        }

        int cuotasRestantes = nTotalCuotas - (inicioCuotaRegular - 1);
        if (cuotasRestantes <= 0) {
            throw new RuntimeException("No quedan cuotas después del período de gracia.");
        }

        // Cuota francesa para las cuotas restantes
        double cuota = (saldo * tasaPeriodica) /
                (1 - Math.pow(1 + tasaPeriodica, -cuotasRestantes));

        // 5.2. CUOTAS REGULARES (sin gracia)
        for (int i = inicioCuotaRegular; i <= nTotalCuotas; i++) {
            CronogramaDTO c = new CronogramaDTO();
            c.setNumero_cuota(i);
            c.setSaldo_inicial(saldo);

            double interes = saldo * tasaPeriodica;
            double amortizacion = cuota - interes;
            double seguroDes = pctSegDesPeriodo * saldo;  // usa saldo inicial
            double seguroRiesgo = seguroRiesgoPeriodo;

            saldo -= amortizacion;
            if (saldo < 0) {
                saldo = 0;
            }

            c.setInteres(interes);
            c.setAmortizacion(amortizacion);
            c.setCuota_incluye_seguro_desgravamen(cuota);
            c.setSaldo_final(saldo);
            c.setComision(comisionPeriodica);
            c.setPortes(portes);
            c.setGastos_administracion(gastosAdmin);
            c.setSeguro_desgravamen(seguroDes);
            c.setSeguro_riesgo(seguroRiesgo);
            c.setTea(tea);
            c.setTasa_periodica(tasaPeriodica);
            c.setPeriodo_gracia("Ninguno");

            // Acumular totales
            totalIntereses += interes;
            totalAmort += amortizacion;
            totalSegDes += seguroDes;
            totalSegRiesgo += seguroRiesgo;
            totalComisiones += comisionPeriodica;
            totalPortesGastos += (portes + gastosAdmin);

            cronograma.add(c);
        }

        // ========= 6. GUARDAR TOTALES EN DATOS =========
        datos.setTotal_intereses(totalIntereses);
        datos.setTotal_amortizacion_capital(totalAmort);
        datos.setTotal_seguro_desgravamen(totalSegDes);
        datos.setTotal_seguro_riesgo(totalSegRiesgo);
        datos.setTotal_comisiones_periodicas(totalComisiones);
        datos.setTotal_portes_y_gastos_adm(totalPortesGastos);

        System.out.println("Tasa periódica    : " + tasaPeriodica);
        System.out.println("TEA equivalente   : " + tea);
        System.out.println("Frecuencia pago(d): " + frecuenciaPago);
        System.out.println("Cuotas/año        : " + nCuotasPorAnho);
        System.out.println("Total de cuotas   : " + nTotalCuotas);
        System.out.println("=======================================\n");



        // ========= 7. RESPUESTA =========
        SimulacionFrancesResponseDTO response = new SimulacionFrancesResponseDTO();
        response.setDatos(datos);
        response.setCronograma(cronograma);

        return response;


    }

    @Override
    public List<SimulacionFrancesResponseDTO> simularFrancesBatch(List<DatosCronogramaDTO> listaDatos) {
        List<SimulacionFrancesResponseDTO> resultados = new ArrayList<>();
        for (DatosCronogramaDTO d : listaDatos) {
            resultados.add(simularFrances(d));
        }
        return resultados;
    }
}
