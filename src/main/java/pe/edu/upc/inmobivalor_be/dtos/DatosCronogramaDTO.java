package pe.edu.upc.inmobivalor_be.dtos;

public class DatosCronogramaDTO {
    private double precio_venta_activo;
    private double porcentaje_cuota_inicial;
    private int numero_anhos;
    private int frecuencia_pago;
    private int numero_dias_por_anho;

    // ... de los costes/gastos iniciales
    private double costes_notariales;
    private double costes_registrales;
    private double tasacion;
    private double comision_estudio;
    private double comision_activacion;

    // ... de los costes/gastos periodicos
    private double comision_periodica;
    private double portes;
    private double gastos_administracion;
    private double porcentaje_seguro_desgravamen;
    private double porcentaje_seguro_riesgo;

    // ... del costo de oportunidad
    private double tasa_descuento;

    // ... del financiamiento
    private double saldo_a_financiar;
    private double monto_prestamo;
    private int numero_cuotas_por_anho;
    private int numero_total_cuotas;

    // ... totales por costes/gastos
    private double total_intereses;
    private double total_amortizacion_capital;
    private double total_seguro_desgravamen;
    private double total_seguro_riesgo;
    private double total_comisiones_periodicas;
    private double total_portes_y_gastos_adm;

    public double getComision_periodica() {
        return comision_periodica;
    }

    public void setComision_periodica(double comision_periodica) {
        this.comision_periodica = comision_periodica;
    }

    public double getComision_activacion() {
        return comision_activacion;
    }

    public void setComision_activacion(double comision_activacion) {
        this.comision_activacion = comision_activacion;
    }

    public double getComision_estudio() {
        return comision_estudio;
    }

    public void setComision_estudio(double comision_estudio) {
        this.comision_estudio = comision_estudio;
    }

    public double getTasacion() {
        return tasacion;
    }

    public void setTasacion(double tasacion) {
        this.tasacion = tasacion;
    }

    public double getCostes_registrales() {
        return costes_registrales;
    }

    public void setCostes_registrales(double costes_registrales) {
        this.costes_registrales = costes_registrales;
    }

    public double getCostes_notariales() {
        return costes_notariales;
    }

    public void setCostes_notariales(double costes_notariales) {
        this.costes_notariales = costes_notariales;
    }

    public int getNumero_dias_por_anho() {
        return numero_dias_por_anho;
    }

    public void setNumero_dias_por_anho(int numero_dias_por_anho) {
        this.numero_dias_por_anho = numero_dias_por_anho;
    }

    public int getFrecuencia_pago() {
        return frecuencia_pago;
    }

    public void setFrecuencia_pago(int frecuencia_pago) {
        this.frecuencia_pago = frecuencia_pago;
    }

    public int getNumero_anhos() {
        return numero_anhos;
    }

    public void setNumero_anhos(int numero_anhos) {
        this.numero_anhos = numero_anhos;
    }

    public double getPorcentaje_cuota_inicial() {
        return porcentaje_cuota_inicial;
    }

    public void setPorcentaje_cuota_inicial(double porcentaje_cuota_inicial) {
        this.porcentaje_cuota_inicial = porcentaje_cuota_inicial;
    }

    public double getPrecio_venta_activo() {
        return precio_venta_activo;
    }

    public void setPrecio_venta_activo(double precio_venta_activo) {
        this.precio_venta_activo = precio_venta_activo;
    }

    public double getPortes() {
        return portes;
    }

    public void setPortes(double portes) {
        this.portes = portes;
    }

    public double getGastos_administracion() {
        return gastos_administracion;
    }

    public void setGastos_administracion(double gastos_administracion) {
        this.gastos_administracion = gastos_administracion;
    }

    public double getPorcentaje_seguro_desgravamen() {
        return porcentaje_seguro_desgravamen;
    }

    public void setPorcentaje_seguro_desgravamen(double porcentaje_seguro_desgravamen) {
        this.porcentaje_seguro_desgravamen = porcentaje_seguro_desgravamen;
    }

    public double getPorcentaje_seguro_riesgo() {
        return porcentaje_seguro_riesgo;
    }

    public void setPorcentaje_seguro_riesgo(double porcentaje_seguro_riesgo) {
        this.porcentaje_seguro_riesgo = porcentaje_seguro_riesgo;
    }

    public double getTasa_descuento() {
        return tasa_descuento;
    }

    public void setTasa_descuento(double tasa_descuento) {
        this.tasa_descuento = tasa_descuento;
    }

    public double getSaldo_a_financiar() {
        return saldo_a_financiar;
    }

    public void setSaldo_a_financiar(double saldo_a_financiar) {
        this.saldo_a_financiar = saldo_a_financiar;
    }

    public double getMonto_prestamo() {
        return monto_prestamo;
    }

    public void setMonto_prestamo(double monto_prestamo) {
        this.monto_prestamo = monto_prestamo;
    }

    public int getNumero_cuotas_por_anho() {
        return numero_cuotas_por_anho;
    }

    public void setNumero_cuotas_por_anho(int numero_cuotas_por_anho) {
        this.numero_cuotas_por_anho = numero_cuotas_por_anho;
    }

    public int getNumero_total_cuotas() {
        return numero_total_cuotas;
    }

    public void setNumero_total_cuotas(int numero_total_cuotas) {
        this.numero_total_cuotas = numero_total_cuotas;
    }

    public double getTotal_intereses() {
        return total_intereses;
    }

    public void setTotal_intereses(double total_intereses) {
        this.total_intereses = total_intereses;
    }

    public double getTotal_amortizacion_capital() {
        return total_amortizacion_capital;
    }

    public void setTotal_amortizacion_capital(double total_amortizacion_capital) {
        this.total_amortizacion_capital = total_amortizacion_capital;
    }

    public double getTotal_seguro_desgravamen() {
        return total_seguro_desgravamen;
    }

    public void setTotal_seguro_desgravamen(double total_seguro_desgravamen) {
        this.total_seguro_desgravamen = total_seguro_desgravamen;
    }

    public double getTotal_seguro_riesgo() {
        return total_seguro_riesgo;
    }

    public void setTotal_seguro_riesgo(double total_seguro_riesgo) {
        this.total_seguro_riesgo = total_seguro_riesgo;
    }

    public double getTotal_comisiones_periodicas() {
        return total_comisiones_periodicas;
    }

    public void setTotal_comisiones_periodicas(double total_comisiones_periodicas) {
        this.total_comisiones_periodicas = total_comisiones_periodicas;
    }

    public double getTotal_portes_y_gastos_adm() {
        return total_portes_y_gastos_adm;
    }

    public void setTotal_portes_y_gastos_adm(double total_portes_y_gastos_adm) {
        this.total_portes_y_gastos_adm = total_portes_y_gastos_adm;
    }
}
