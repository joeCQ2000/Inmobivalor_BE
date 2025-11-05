package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tasa_interes")
public class TasaInteres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tasa_interes;

    @Column(name = "tipo_tasa_interes", nullable = false, length = 50)
    private String tipo_tasa_interes;

    @Column(name = "tasa_interes", nullable = false, length = 50)
    private double tasa_interes;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    public TasaInteres() {}

    public TasaInteres(int id_tasa_interes, String tipo_tasa_interes, double tasa_interes) {
        this.id_tasa_interes = id_tasa_interes;
        this.tipo_tasa_interes = tipo_tasa_interes;
        this.tasa_interes = tasa_interes;

    }

    public int getId_tasa_interes() {
        return id_tasa_interes;
    }

    public void setId_tasa_interes(int id_tasa_interes) {
        this.id_tasa_interes = id_tasa_interes;
    }

    public String getTipo_tasa_interes() {
        return tipo_tasa_interes;
    }

    public void setTipo_tasa_interes(String tipo_tasa_interes) {
        this.tipo_tasa_interes = tipo_tasa_interes;
    }

    public double getTasa_interes() {
        return tasa_interes;
    }

    public void setTasa_interes(double tasa_interes) {
        this.tasa_interes = tasa_interes;
    }


}
