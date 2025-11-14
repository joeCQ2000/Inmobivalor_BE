package pe.edu.upc.inmobivalor_be.dtos;

public class DetalleDTO {
    private double precio ;
    private String moneda;
    private double cuota_inicial;
    private double monto_bono;
    private String nombre_entidad;
    private String tipo_tasa;
    private double tasa_pct;
    private int plazo_meses;
    private String plazo_gracia;

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public double getCuota_inicial() {
        return cuota_inicial;
    }

    public void setCuota_inicial(double cuota_inicial) {
        this.cuota_inicial = cuota_inicial;
    }

    public double getMonto_bono() {
        return monto_bono;
    }

    public void setMonto_bono(double monto_bono) {
        this.monto_bono = monto_bono;
    }

    public String getNombre_entidad() {
        return nombre_entidad;
    }

    public void setNombre_entidad(String nombre_entidad) {
        this.nombre_entidad = nombre_entidad;
    }

    public double getTasa_pct() {
        return tasa_pct;
    }

    public void setTasa_pct(double tasa_pct) {
        this.tasa_pct = tasa_pct;
    }

    public String getTipo_tasa() {
        return tipo_tasa;
    }

    public void setTipo_tasa(String tipo_tasa) {
        this.tipo_tasa = tipo_tasa;
    }

    public int getPlazo_meses() {
        return plazo_meses;
    }

    public void setPlazo_meses(int plazo_meses) {
        this.plazo_meses = plazo_meses;
    }

    public String getPlazo_gracia() {
        return plazo_gracia;
    }

    public void setPlazo_gracia(String plazo_gracia) {
        this.plazo_gracia = plazo_gracia;
    }
}
