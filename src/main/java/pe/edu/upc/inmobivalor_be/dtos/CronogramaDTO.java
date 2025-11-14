package pe.edu.upc.inmobivalor_be.dtos;

public class CronogramaDTO {
    private int numero_cuota;                             // Nº
    private double tea;                                   // TEA
    private double tasa_periodica;                        // i' = TEP = TEM
    private double ia;                                    // IA
    private double ip;                                    // IP
    private String periodo_gracia;                           // P.G.

    private double saldo_inicial;
    private double saldo_inicial_indexado;
    private double interes;
    private double cuota_incluye_seguro_desgravamen;      // Cuota (inc Seg Des)
    private double amortizacion;
    private double prepago;
    private double seguro_desgravamen;
    private double seguro_riesgo;
    private double comision;
    private double portes;
    private double gastos_administracion;
    private double saldo_final;
    private double flujo;

    public double getTea() {
        return tea;
    }

    public void setTea(double tea) {
        this.tea = tea;
    }

    public int getNumero_cuota() {
        return numero_cuota;
    }

    public void setNumero_cuota(int numero_cuota) {
        this.numero_cuota = numero_cuota;
    }

    public double getTasa_periodica() {
        return tasa_periodica;
    }

    public void setTasa_periodica(double tasa_periodica) {
        this.tasa_periodica = tasa_periodica;
    }

    public double getIa() {
        return ia;
    }

    public void setIa(double ia) {
        this.ia = ia;
    }

    public double getIp() {
        return ip;
    }

    public void setIp(double ip) {
        this.ip = ip;
    }

    public String getPeriodo_gracia() {
        return periodo_gracia;
    }

    public void setPeriodo_gracia(String periodo_gracia) {
        this.periodo_gracia = periodo_gracia;
    }

    public double getSaldo_inicial() {
        return saldo_inicial;
    }

    public void setSaldo_inicial(double saldo_inicial) {
        this.saldo_inicial = saldo_inicial;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public double getSaldo_inicial_indexado() {
        return saldo_inicial_indexado;
    }

    public void setSaldo_inicial_indexado(double saldo_inicial_indexado) {
        this.saldo_inicial_indexado = saldo_inicial_indexado;
    }

    public double getCuota_incluye_seguro_desgravamen() {
        return cuota_incluye_seguro_desgravamen;
    }

    public void setCuota_incluye_seguro_desgravamen(double cuota_incluye_seguro_desgravamen) {
        this.cuota_incluye_seguro_desgravamen = cuota_incluye_seguro_desgravamen;
    }

    public double getPrepago() {
        return prepago;
    }

    public void setPrepago(double prepago) {
        this.prepago = prepago;
    }

    public double getAmortizacion() {
        return amortizacion;
    }

    public void setAmortizacion(double amortizacion) {
        this.amortizacion = amortizacion;
    }

    public double getSeguro_desgravamen() {
        return seguro_desgravamen;
    }

    public void setSeguro_desgravamen(double seguro_desgravamen) {
        this.seguro_desgravamen = seguro_desgravamen;
    }

    public double getSeguro_riesgo() {
        return seguro_riesgo;
    }

    public void setSeguro_riesgo(double seguro_riesgo) {
        this.seguro_riesgo = seguro_riesgo;
    }

    public double getPortes() {
        return portes;
    }

    public void setPortes(double portes) {
        this.portes = portes;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public double getGastos_administracion() {
        return gastos_administracion;
    }

    public void setGastos_administracion(double gastos_administracion) {
        this.gastos_administracion = gastos_administracion;
    }

    public double getSaldo_final() {
        return saldo_final;
    }

    public void setSaldo_final(double saldo_final) {
        this.saldo_final = saldo_final;
    }

    public double getFlujo() {
        return flujo;
    }

    public void setFlujo(double flujo) {
        this.flujo = flujo;
    }
}
