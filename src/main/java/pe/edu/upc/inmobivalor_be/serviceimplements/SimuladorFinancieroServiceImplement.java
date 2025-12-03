package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.dtos.CronogramaDTO;
import pe.edu.upc.inmobivalor_be.dtos.DatosCronogramaDTO;
import pe.edu.upc.inmobivalor_be.dtos.IndicadoresFinancierosDTO;
import pe.edu.upc.inmobivalor_be.dtos.SimulacionFrancesResponseDTO;
import pe.edu.upc.inmobivalor_be.entities.CreditoPrestamo;
import pe.edu.upc.inmobivalor_be.entities.Entidad_tasa;
import pe.edu.upc.inmobivalor_be.repositories.ICreditoPrestamoRepository;
import pe.edu.upc.inmobivalor_be.repositories.IEntidad_tasaRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IIndicadoresFinancierosService;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ISimuladorFinancieroService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimuladorFinancieroServiceImplement implements ISimuladorFinancieroService {

    @Autowired
    private IEntidad_tasaRepository entidadTasaRepository;

    @Autowired
    private ICreditoPrestamoRepository creditoPrestamoRepository;

    @Autowired
    private IIndicadoresFinancierosService indicadoresFinancierosService;

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

        // Tipo y meses de gracia
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
        double precio = datos.getPrecio_venta_activo();
        double pctCuotaInicial = datos.getPorcentaje_cuota_inicial();
        int diasPorAnho = datos.getNumero_dias_por_anho();

        double costesNotariales = datos.getCostes_notariales();
        double costesRegistrales = datos.getCostes_registrales();
        double tasacion = datos.getTasacion();
        double comisionEstudio = datos.getComision_estudio();
        double comisionActivacion = datos.getComision_activacion();

        double comisionPeriodica = datos.getComision_periodica();
        double portes = datos.getPortes();
        double gastosAdmin = datos.getGastos_administracion();

        double pctSegDesMensual = datos.getPorcentaje_seguro_desgravamen();
        double pctSegRiesgoAnual = datos.getPorcentaje_seguro_riesgo();
        double tasaDescuentoAnual = datos.getTasa_descuento();

        // ========= 2. DERIVADOS BÁSICOS =========
        double saldoFinanciar = precio - (precio * pctCuotaInicial);

        double montoPrestamo = saldoFinanciar
                + costesNotariales
                + costesRegistrales
                + tasacion
                + comisionEstudio
                + comisionActivacion;

        datos.setSaldo_a_financiar(saldoFinanciar);
        datos.setMonto_prestamo(montoPrestamo);

        // ========= 3. TASA DESDE BD =========
        int entidadId = datos.getEntidadId();

        List<Entidad_tasa> entidadTasas = entidadTasaRepository.findActivasByEntidad(entidadId);
        if (entidadTasas.isEmpty()) {
            throw new RuntimeException("No se encontró ninguna tasa activa para la entidad " + entidadId);
        }

        Entidad_tasa entidadTasa = entidadTasas.get(0);

        String tipoTasa = entidadTasa.getTasa().getTipo_tasa();   // "TEA", "TNA", "TN", etc.

        double tasaBruta = entidadTasa.getTasa().getTasa_pct();
        double tasaAnual = (tasaBruta > 1.0) ? tasaBruta / 100.0 : tasaBruta;

        boolean esNominal = tipoTasa != null &&
                (tipoTasa.equalsIgnoreCase("TN")
                        || tipoTasa.equalsIgnoreCase("TNA")
                        || tipoTasa.toUpperCase().startsWith("TN"));

        boolean esEfectiva = tipoTasa != null &&
                (tipoTasa.equalsIgnoreCase("TEA")
                        || tipoTasa.toUpperCase().startsWith("TE"));

        // ========= 3.1 FRECUENCIA DE PAGO =========
        int frecuenciaPago; // días por periodo de pago

        if (esNominal) {
            if ("diaria".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 1;
            } else if ("semanal".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 7;
            } else if ("quincenal".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 15;
            } else if ("mensual".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 30;
            } else if ("bimestral".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 60;
            } else if ("trimestral".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 90;
            } else if ("semestral".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 180;
            } else if ("anual".equalsIgnoreCase(capitalizacion)) {
                frecuenciaPago = 360;
            } else {
                throw new IllegalArgumentException(
                        "Capitalización no soportada para tasa nominal: " + capitalizacion);
            }
        } else if (esEfectiva) {
            int frecuenciaDesdeFront = datos.getFrecuencia_pago();
            frecuenciaPago = (frecuenciaDesdeFront > 0) ? frecuenciaDesdeFront : 360;
        } else {
            throw new IllegalArgumentException("Tipo de tasa de interés no soportado: " + tipoTasa);
        }

        datos.setFrecuencia_pago(frecuenciaPago);

        int nCuotasPorAnho = diasPorAnho / frecuenciaPago;
        int nTotalCuotas = numeroAnhos * nCuotasPorAnho;

        double diasPorPeriodo = frecuenciaPago;

        datos.setNumero_cuotas_por_anho(nCuotasPorAnho);
        datos.setNumero_total_cuotas(nTotalCuotas);

        // ========= 3.2 CÁLCULO DE TASAS PERIÓDICAS =========
        double tea;
        double tasaPeriodica;

        if (esEfectiva) {
            tea = tasaAnual;
            tasaPeriodica = Math.pow(1.0 + tea, diasPorPeriodo / (double) diasPorAnho) - 1.0;
        } else {
            double nPeriodosAnho = (double) nCuotasPorAnho;
            tasaPeriodica = tasaAnual / nPeriodosAnho;
            tea = Math.pow(1.0 + tasaPeriodica, nPeriodosAnho) - 1.0;
        }

        // ========= 4. SEGUROS Y DESCUENTO POR PERIODO =========
        double pctSegDesPeriodo = pctSegDesMensual / 30.0 * diasPorPeriodo;
        double seguroRiesgoPeriodo = pctSegRiesgoAnual * precio / nCuotasPorAnho;

        double tasaDescPeriodo = Math.pow(1.0 + tasaDescuentoAnual,
                diasPorPeriodo / (double) diasPorAnho) - 1.0;

        // ========= 5. CRONOGRAMA MÉTODO FRANCÉS =========
        List<CronogramaDTO> cronograma = new ArrayList<>();

        double saldo = montoPrestamo;

        double totalIntereses = 0.0;
        double totalAmort = 0.0;
        double totalSegDes = 0.0;
        double totalSegRiesgo = 0.0;
        double totalComisiones = 0.0;
        double totalPortesGastos = 0.0;

        double prepago = datos.getPrepago();
        int cuotaPrepago = datos.getCuotaPrepago();

        int inicioCuotaRegular = 1;

        // --- Gracia TOTAL ---
        if ("total".equalsIgnoreCase(tipoGracia) && mesesGracia > 0) {

            for (int i = 1; i <= mesesGracia; i++) {
                CronogramaDTO c = new CronogramaDTO();
                c.setNumero_cuota(i);
                c.setSaldo_inicial(saldo);

                double interes = saldo * tasaPeriodica;
                double seguroDes = pctSegDesPeriodo * saldo;
                double seguroRiesgo = seguroRiesgoPeriodo;

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

                totalIntereses += interes;
                totalSegDes += seguroDes;
                totalSegRiesgo += seguroRiesgo;
                totalComisiones += comisionPeriodica;
                totalPortesGastos += (portes + gastosAdmin);

                cronograma.add(c);
                saldo = saldoFinal;
            }

            inicioCuotaRegular = mesesGracia + 1;

            // --- Gracia PARCIAL ---
        } else if ("parcial".equalsIgnoreCase(tipoGracia) && mesesGracia > 0) {

            for (int i = 1; i <= mesesGracia; i++) {
                CronogramaDTO c = new CronogramaDTO();
                c.setNumero_cuota(i);
                c.setSaldo_inicial(saldo);

                double interes = saldo * tasaPeriodica;
                double seguroDes = pctSegDesPeriodo * saldo;
                double seguroRiesgo = seguroRiesgoPeriodo;

                double cuotaParcial = interes + seguroDes;

                double saldoFinal = saldo;

                c.setInteres(interes);
                c.setAmortizacion(0.0);
                c.setCuota_incluye_seguro_desgravamen(cuotaParcial);
                c.setSaldo_final(saldoFinal);
                c.setComision(comisionPeriodica);
                c.setPortes(portes);
                c.setGastos_administracion(gastosAdmin);
                c.setSeguro_desgravamen(seguroDes);
                c.setSeguro_riesgo(seguroRiesgo);
                c.setTea(tea);
                c.setTasa_periodica(tasaPeriodica);
                c.setPeriodo_gracia("Parcial");
                c.setPrepago(0.0);

                totalIntereses += interes;
                totalSegDes += seguroDes;
                totalSegRiesgo += seguroRiesgo;
                totalComisiones += comisionPeriodica;
                totalPortesGastos += (portes + gastosAdmin);

                cronograma.add(c);
            }

            inicioCuotaRegular = mesesGracia + 1;
        }

        int cuotasRestantes = nTotalCuotas - (inicioCuotaRegular - 1);
        if (cuotasRestantes <= 0) {
            throw new RuntimeException("No quedan cuotas después del período de gracia.");
        }

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
                double seguroDes = pctSegDesPeriodo * saldo;
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

            double cuotaAntes = calcularCuotaConstanteConSeguro(
                    saldo,
                    tasaPeriodica,
                    pctSegDesPeriodo,
                    cuotasRestantes
            );

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

            // Cuota donde se aplica el prepago
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
                c.setCuota_incluye_seguro_desgravamen(cuotaAntes);
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
                totalAmort += amortizacion + prepago;
                totalSegDes += seguroDes;
                totalSegRiesgo += seguroRiesgo;
                totalComisiones += comisionPeriodica;
                totalPortesGastos += (portes + gastosAdmin);

                cronograma.add(c);

                saldo = saldoDespues;
            }

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
                    c.setCuota_incluye_seguro_desgravamen(cuotaDespues);
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

        // ========= 7. INDICADORES (copiados del Excel) =========

        // 7.1 Flujos tipo columna T (T25:Tn)
        List<Double> flujos = new ArrayList<>();
        // T25: monto del préstamo (positivo)
        flujos.add(montoPrestamo);

        for (CronogramaDTO c : cronograma) {

            double cuotaBase;

            if ("Total".equalsIgnoreCase(c.getPeriodo_gracia())) {
                // Gracia total: sólo seguro desgravamen
                cuotaBase = c.getSeguro_desgravamen();
            } else if ("Parcial".equalsIgnoreCase(c.getPeriodo_gracia())) {
                // Gracia parcial: interés + seguro desgravamen
                cuotaBase = c.getInteres() + c.getSeguro_desgravamen();
            } else {
                // Sin gracia: cuota completa (interés + amort + seg. desgrav.)
                cuotaBase = c.getCuota_incluye_seguro_desgravamen();
            }

            double pagoPeriodo =
                    cuotaBase
                            + c.getPrepago()
                            + c.getSeguro_riesgo()
                            + c.getComision()
                            + c.getPortes()
                            + c.getGastos_administracion();

            double flujo = -pagoPeriodo; // eg. -745.26 en el Excel

            c.setFlujo(flujo);
            flujos.add(flujo);
        }

        // 7.2 TIR por periodo (I20 = IRR(T25:Tn))
        double tirPeriodo = calcularTIR(flujos);

        // 7.3 TCEA de la operación (I21 = (1+I20)^(I6)-1)
        double tceaOperacion = Math.pow(1.0 + tirPeriodo, nCuotasPorAnho) - 1.0;

        // 7.4 VAN operación (I22 = T25 + NPV(I19,T26:Tn))
        double vanOperacion = calcularVAN(tasaDescPeriodo, flujos);

        // 7.5 Guardar indicadores en BD
        IndicadoresFinancierosDTO indicadoresDTO = new IndicadoresFinancierosDTO();
        indicadoresDTO.setFecha_calculo(LocalDate.now());
        indicadoresDTO.setId_credito(credito.getId_credito());
        indicadoresDTO.setTir(tirPeriodo);         // igual al Excel I20 (decimal)
        indicadoresDTO.setTcea(tceaOperacion);     // igual al Excel I21 (decimal)
        indicadoresDTO.setTrea(tea);               // aquí puedes ajustar si quieres otro cálculo
        indicadoresDTO.setVan(vanOperacion);

        indicadoresFinancierosService.insert(indicadoresDTO);

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
        System.out.println("TIR periodo          : " + tirPeriodo);
        System.out.println("TCEA operación       : " + tceaOperacion);
        System.out.println("VAN operación        : " + vanOperacion);
        System.out.println("===============================================\n");

        // ========= 8. RESPUESTA =========
        SimulacionFrancesResponseDTO response = new SimulacionFrancesResponseDTO();
        response.setDatos(datos);
        response.setCronograma(cronograma);
        response.setIndicadores(indicadoresDTO);

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

    // === Helpers privados ================================================

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
        double high = saldoInicial;

        while (simularSaldoFinal(saldoInicial, tasaPeriodica, pctSegDesPeriodo, numeroCuotas, high) > 0) {
            high *= 1.5;
        }

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

    // === VAN / TIR tipo Excel ============================================

    private double calcularNPV(double tasa, List<Double> flujos) {
        double npv = 0.0;
        for (int t = 0; t < flujos.size(); t++) {
            npv += flujos.get(t) / Math.pow(1.0 + tasa, t);
        }
        return npv;
    }

    /**
     * VAN al estilo I22 = T25 + NPV(I19, T26:Tn)
     */
    private double calcularVAN(double tasaPeriodoDescuento, List<Double> flujos) {
        if (flujos.isEmpty()) return 0.0;

        double flujo0 = flujos.get(0);
        double npvFuturos = 0.0;

        for (int t = 1; t < flujos.size(); t++) {
            npvFuturos += flujos.get(t) / Math.pow(1.0 + tasaPeriodoDescuento, t);
        }

        return flujo0 + npvFuturos;
    }

    /**
     * TIR por periodo usando bisección, equivalente a IRR(T25:Tn, 1%)
     */
    private double calcularTIR(List<Double> flujos) {
        double low = -0.9999;
        double high = 1.0;

        double npvLow = calcularNPV(low, flujos);
        double npvHigh = calcularNPV(high, flujos);

        int iter = 0;
        while (npvLow * npvHigh > 0 && iter < 100) {
            high *= 2.0;
            npvHigh = calcularNPV(high, flujos);
            iter++;
        }

        for (int i = 0; i < 100; i++) {
            double mid = (low + high) / 2.0;
            double npvMid = calcularNPV(mid, flujos);

            if (Math.abs(npvMid) < 1e-10) {
                return mid;
            }

            if (npvLow * npvMid < 0) {
                high = mid;
                npvHigh = npvMid;
            } else {
                low = mid;
                npvLow = npvMid;
            }
        }

        return (low + high) / 2.0;
    }
}
