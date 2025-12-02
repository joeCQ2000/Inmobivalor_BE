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

        int plazoMeses;
        try {
            plazoMeses = Integer.parseInt(credito.getPlazo_meses().trim());
        } catch (NumberFormatException e) {
            throw new RuntimeException(
                    "El campo plazo_meses del crédito debe ser numérico (en meses). Valor actual: "
                            + credito.getPlazo_meses());
        }

        // Años aproximados a partir del plazo en meses
        int numeroAnhos = plazoMeses / 12;
        if (plazoMeses % 12 != 0) {
            numeroAnhos++;
        }
        datos.setNumero_anhos(numeroAnhos);

        // Tipo y meses de gracia: se prioriza lo enviado por el front,
        // si viene vacío/0 se toma lo de BD.
        String tipoGracia = datos.getTipo_gracia();
        if (tipoGracia == null || tipoGracia.isBlank()) {
            tipoGracia = credito.getTipo_gracia();
            datos.setTipo_gracia(tipoGracia);
        }

        int mesesGracia = datos.getMeses_gracia();
        if (mesesGracia <= 0) {
            mesesGracia = credito.getMeses_gracia();
            datos.setMeses_gracia(mesesGracia);
        }

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
        double tasaDescuentoAnual = datos.getTasa_descuento();              // ej. 0.27

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

        // ========= 3.1 FRECUENCIA DE PAGO (días por periodo) =========
        int frecuenciaPago; // días por periodo de pago

        if (esNominal) {
            // Capitalización SOLO aplica para tasas nominales
            if ("diaria".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 1;       // 1 día
            } else if ("semanal".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 7;       // 7 días
            } else if ("quincenal".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 15;      // 15 días (aprox)
            } else if ("mensual".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 30;      // 30 días
            } else if ("bimestral".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 60;      // 60 días
            } else if ("trimestral".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 90;      // 90 días
            } else if ("semestral".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 180;     // 180 días
            } else if ("anual".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 360;     // 360 días
            } else {
                throw new IllegalArgumentException(
                        "Capitalización no soportada para tasa nominal: " + capitalizacion);
            }
        } else if (esEfectiva) {
            // Para TEA: NO usar capitalización (por definición ya es efectiva anual)
            // Regla: si es tasa efectiva, por defecto 360,
            // pero si el front envía frecuencia_pago (>0), se respeta (ej: 90).
            int frecuenciaDesdeFront = datos.getFrecuencia_pago();
            frecuenciaPago = (frecuenciaDesdeFront > 0) ? frecuenciaDesdeFront : 360;
        } else {
            throw new IllegalArgumentException("Tipo de tasa de interés no soportado: " + tipoTasa);
        }

        // Guardamos en el DTO la frecuencia de pago efectiva usada
        datos.setFrecuencia_pago(frecuenciaPago);

        // Nº cuotas por año = días por año / días por periodo
        int nCuotasPorAnho = diasPorAnho / frecuenciaPago;

        // Nº total de cuotas = años * cuotas/año
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
            // Tasa nominal anual j -> aproximación estándar simple
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
        double tasaDescPeriodo = Math.pow(1.0 + tasaDescuentoAnual,
                diasPorPeriodo / (double) diasPorAnho) - 1.0;

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

        // Datos de prepago
        double prepago = datos.getPrepago();        // ej. 10000
        int cuotaPrepago = datos.getCuotaPrepago(); // ej. 5

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
                c.setPrepago(0.0);

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

        /*
         * CASO A: sin prepago -> una sola cuota constante.
         * CASO B: con prepago -> 2 tramos:
         *   - tramo 1: desde inicioCuotaRegular hasta cuotaPrepago (cuotaAntes)
         *   - tramo 2: desde cuotaPrepago+1 hasta nTotalCuotas (cuotaDespues)
         */
        boolean hayPrepagoValido = prepago > 0
                && cuotaPrepago >= inicioCuotaRegular
                && cuotaPrepago <= nTotalCuotas;

        if (!hayPrepagoValido) {
            // ====== CASO A: SIN PREPAGO ======
            double cuotaIncSegDes = calcularCuotaConstanteConSeguro(
                    saldo,
                    tasaPeriodica,
                    pctSegDesPeriodo,
                    cuotasRestantes
            );

            for (int i = inicioCuotaRegular; i <= nTotalCuotas; i++) {
                CronogramaDTO c = new CronogramaDTO();
                c.setNumero_cuota(i);
                c.setSaldo_inicial(saldo);

                double interes = saldo * tasaPeriodica;
                double seguroDes = pctSegDesPeriodo * saldo;  // usa saldo inicial
                double seguroRiesgo = seguroRiesgoPeriodo;

                double amortizacion = cuotaIncSegDes - interes - seguroDes;
                if (amortizacion < 0) {
                    amortizacion = 0;
                }

                saldo -= amortizacion;
                if (saldo < 0) {
                    saldo = 0;
                }

                c.setInteres(interes);
                c.setAmortizacion(amortizacion);
                c.setCuota_incluye_seguro_desgravamen(cuotaIncSegDes);
                c.setSaldo_final(saldo);
                c.setComision(comisionPeriodica);
                c.setPortes(portes);
                c.setGastos_administracion(gastosAdmin);
                c.setSeguro_desgravamen(seguroDes);
                c.setSeguro_riesgo(seguroRiesgo);
                c.setTea(tea);
                c.setTasa_periodica(tasaPeriodica);
                c.setPeriodo_gracia("Ninguno");
                c.setPrepago(0.0);

                // Acumular totales
                totalIntereses += interes;
                totalAmort += amortizacion;
                totalSegDes += seguroDes;
                totalSegRiesgo += seguroRiesgo;
                totalComisiones += comisionPeriodica;
                totalPortesGastos += (portes + gastosAdmin);

                cronograma.add(c);
            }

        } else {
            // ====== CASO B: CON PREPAGO ======

            // 1) Cuota base como si NO hubiera prepago (para las cuotas restantes)
            double cuotaAntes = calcularCuotaConstanteConSeguro(
                    saldo,
                    tasaPeriodica,
                    pctSegDesPeriodo,
                    cuotasRestantes
            );

            // 1.1) Cuotas regulares ANTES de la cuota de prepago
            for (int i = inicioCuotaRegular; i < cuotaPrepago; i++) {

                CronogramaDTO c = new CronogramaDTO();
                c.setNumero_cuota(i);
                c.setSaldo_inicial(saldo);

                double interes = saldo * tasaPeriodica;
                double seguroDes = pctSegDesPeriodo * saldo;
                double seguroRiesgo = seguroRiesgoPeriodo;

                double amortizacion = cuotaAntes - interes - seguroDes;
                if (amortizacion < 0) amortizacion = 0;

                saldo -= amortizacion;
                if (saldo < 0) saldo = 0;

                c.setInteres(interes);
                c.setAmortizacion(amortizacion);
                c.setCuota_incluye_seguro_desgravamen(cuotaAntes);
                c.setSaldo_final(saldo);
                c.setComision(comisionPeriodica);
                c.setPortes(portes);
                c.setGastos_administracion(gastosAdmin);
                c.setSeguro_desgravamen(seguroDes);
                c.setSeguro_riesgo(seguroRiesgo);
                c.setTea(tea);
                c.setTasa_periodica(tasaPeriodica);
                c.setPeriodo_gracia("Ninguno");
                c.setPrepago(0.0);

                totalIntereses += interes;
                totalAmort += amortizacion;
                totalSegDes += seguroDes;
                totalSegRiesgo += seguroRiesgo;
                totalComisiones += comisionPeriodica;
                totalPortesGastos += (portes + gastosAdmin);

                cronograma.add(c);
            }

            // 1.2) Cuota donde se aplica el prepago (ej. cuota 5 en tu Excel)
            {
                int i = cuotaPrepago;

                CronogramaDTO c = new CronogramaDTO();
                c.setNumero_cuota(i);
                c.setSaldo_inicial(saldo);

                double interes = saldo * tasaPeriodica;
                double seguroDes = pctSegDesPeriodo * saldo;
                double seguroRiesgo = seguroRiesgoPeriodo;

                double amortizacion = cuotaAntes - interes - seguroDes;
                if (amortizacion < 0) amortizacion = 0;

                double saldoDespues = saldo - amortizacion - prepago;
                if (saldoDespues < 0) saldoDespues = 0;

                c.setInteres(interes);
                c.setAmortizacion(amortizacion);
                c.setCuota_incluye_seguro_desgravamen(cuotaAntes); // misma cuota que antes del prepago
                c.setSaldo_final(saldoDespues);
                c.setComision(comisionPeriodica);
                c.setPortes(portes);
                c.setGastos_administracion(gastosAdmin);
                c.setSeguro_desgravamen(seguroDes);
                c.setSeguro_riesgo(seguroRiesgo);
                c.setTea(tea);
                c.setTasa_periodica(tasaPeriodica);
                c.setPeriodo_gracia("Ninguno");
                c.setPrepago(prepago);

                totalIntereses += interes;
                totalAmort += amortizacion + prepago; // el prepago también amortiza capital
                totalSegDes += seguroDes;
                totalSegRiesgo += seguroRiesgo;
                totalComisiones += comisionPeriodica;
                totalPortesGastos += (portes + gastosAdmin);

                cronograma.add(c);

                // actualizar saldo para el siguiente tramo
                saldo = saldoDespues;
            }

            // 2) Tramo después del prepago: nueva cuota constante
            int cuotasDespuesPrepago = nTotalCuotas - cuotaPrepago;
            if (cuotasDespuesPrepago > 0 && saldo > 0) {

                double cuotaDespues = calcularCuotaConstanteConSeguro(
                        saldo,
                        tasaPeriodica,
                        pctSegDesPeriodo,
                        cuotasDespuesPrepago
                );

                for (int i = cuotaPrepago + 1; i <= nTotalCuotas; i++) {

                    CronogramaDTO c = new CronogramaDTO();
                    c.setNumero_cuota(i);
                    c.setSaldo_inicial(saldo);

                    double interes = saldo * tasaPeriodica;
                    double seguroDes = pctSegDesPeriodo * saldo;
                    double seguroRiesgo = seguroRiesgoPeriodo;

                    double amortizacion = cuotaDespues - interes - seguroDes;
                    if (amortizacion < 0) amortizacion = 0;

                    saldo -= amortizacion;
                    if (saldo < 0) saldo = 0;

                    c.setInteres(interes);
                    c.setAmortizacion(amortizacion);
                    c.setCuota_incluye_seguro_desgravamen(cuotaDespues); // nueva cuota ≈ 13 346.48
                    c.setSaldo_final(saldo);
                    c.setComision(comisionPeriodica);
                    c.setPortes(portes);
                    c.setGastos_administracion(gastosAdmin);
                    c.setSeguro_desgravamen(seguroDes);
                    c.setSeguro_riesgo(seguroRiesgo);
                    c.setTea(tea);
                    c.setTasa_periodica(tasaPeriodica);
                    c.setPeriodo_gracia("Ninguno");
                    c.setPrepago(0.0);

                    totalIntereses += interes;
                    totalAmort += amortizacion;
                    totalSegDes += seguroDes;
                    totalSegRiesgo += seguroRiesgo;
                    totalComisiones += comisionPeriodica;
                    totalPortesGastos += (portes + gastosAdmin);

                    cronograma.add(c);
                }
            }
        }

        // ========= 6. GUARDAR TOTALES EN DATOS =========
        datos.setTotal_intereses(totalIntereses);
        datos.setTotal_amortizacion_capital(totalAmort);
        datos.setTotal_seguro_desgravamen(totalSegDes);
        datos.setTotal_seguro_riesgo(totalSegRiesgo);
        datos.setTotal_comisiones_periodicas(totalComisiones);
        datos.setTotal_portes_y_gastos_adm(totalPortesGastos);

        // ========= DEBUG EN CONSOLA =========
        System.out.println("=========== DEBUG SIMULACIÓN FRANCÉS ===========");
        System.out.println("Tipo de tasa (BD)    : " + tipoTasa);
        System.out.println("Es nominal           : " + esNominal);
        System.out.println("Es efectiva          : " + esEfectiva);
        System.out.println("Capitalización (cred): " + capitalizacion);
        System.out.println("TEA utilizada        : " + tea);
        System.out.println("Tasa periódica i'    : " + tasaPeriodica);
        System.out.println("Días por periodo     : " + frecuenciaPago);
        System.out.println("Cuotas por año       : " + nCuotasPorAnho);
        System.out.println("Plazo (meses)        : " + plazoMeses);
        System.out.println("Total de cuotas      : " + nTotalCuotas);
        System.out.println("Tipo gracia (DTO)    : " + tipoGracia);
        System.out.println("Periodos de gracia   : " + mesesGracia);
        System.out.println("Monto préstamo       : " + montoPrestamo);
        System.out.println("===============================================\n");

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

    // === Helpers privados para calcular la cuota constante con seguro de desgravamen ===

    private double simularSaldoFinal(
            double saldoInicial,
            double tasaPeriodica,
            double pctSegDesPeriodo,
            int numeroCuotas,
            double cuotaIncSegDes) {

        double saldo = saldoInicial;
        for (int k = 0; k < numeroCuotas; k++) {
            double interes = saldo * tasaPeriodica;
            double seguroDes = saldo * pctSegDesPeriodo;

            double amortizacion = cuotaIncSegDes - interes - seguroDes;
            if (amortizacion < 0) {
                amortizacion = 0;
            }

            saldo -= amortizacion;
        }
        return saldo;
    }

    private double calcularCuotaConstanteConSeguro(
            double saldoInicial,
            double tasaPeriodica,
            double pctSegDesPeriodo,
            int numeroCuotas) {

        double low = 0.0;
        double high = saldoInicial; // cota inicial

        // Asegurar que con "high" el saldo final sea negativo (cuota muy alta)
        while (simularSaldoFinal(saldoInicial, tasaPeriodica, pctSegDesPeriodo, numeroCuotas, high) > 0) {
            high *= 1.5;
        }

        // Búsqueda binaria
        for (int iter = 0; iter < 60; iter++) {
            double mid = (low + high) / 2.0;
            double saldoFinal = simularSaldoFinal(saldoInicial, tasaPeriodica, pctSegDesPeriodo, numeroCuotas, mid);

            if (saldoFinal > 0) {
                low = mid;
            } else {
                high = mid;
            }
        }

        return (low + high) / 2.0;
    }
}
