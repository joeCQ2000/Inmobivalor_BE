package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasa_interes")
public class Tasa_interes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tasa;

    @Column(name = "tipo_tasa_interes", nullable = false, length = 50)
    private String tipo_tasa;

    @Column(name = "tasa_interes", nullable = false, length = 50)
    private double tasa_pct;

    @Column(name = "estado", nullable = false )
    private boolean estado;

    public Tasa_interes() {}

    public Tasa_interes(int id_tasa, String tipo_tasa, double tasa_pct, boolean estado) {
        this.id_tasa = id_tasa;
        this.tipo_tasa = tipo_tasa;
        this.tasa_pct = tasa_pct;
        this.estado = estado;
    }

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
