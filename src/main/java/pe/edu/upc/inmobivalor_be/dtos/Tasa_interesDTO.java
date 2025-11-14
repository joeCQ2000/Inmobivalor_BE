package pe.edu.upc.inmobivalor_be.dtos;

public class Tasa_interesDTO {

    private int id_tasa;

    private String tipo_tasa;

    private double tasa_pct;

    private boolean estado;

    public int getId_tasa() {
        return id_tasa;
    }

    public void setId_tasa(int id_tasa) {
        this.id_tasa = id_tasa;
    }

    public String getTipo_tasa() {
        return tipo_tasa;
    }

    public void setTipo_tasa(String tipo_tasa) {
        this.tipo_tasa = tipo_tasa;
    }

    public double getTasa_pct() {
        return tasa_pct;
    }

    public void setTasa_pct(double tasa_pct) {
        this.tasa_pct = tasa_pct;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
