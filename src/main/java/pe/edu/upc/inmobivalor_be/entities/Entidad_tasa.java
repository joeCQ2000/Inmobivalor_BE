package pe.edu.upc.inmobivalor_be.entities;
import jakarta.persistence.*;

import jakarta.persistence.Id;


@Entity
@Table(name = "entidad_tasa")
public class Entidad_tasa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_entidad_tasa;

    @ManyToOne
    @JoinColumn(name = "id_tasa", nullable = false)
    private Tasa_interes tasa;

    @ManyToOne
    @JoinColumn(name = "id_entidad", nullable = false)
    private Entidad_financiera entidad;

    public Entidad_tasa() {
    }

    public Entidad_tasa(int id_entidad_tasa, Tasa_interes tasa, Entidad_financiera entidad) {
        this.id_entidad_tasa = id_entidad_tasa;
        this.tasa = tasa;
        this.entidad = entidad;
    }

    public int getId_entidad_tasa() {
        return id_entidad_tasa;
    }

    public void setId_entidad_tasa(int id_entidad_tasa) {
        this.id_entidad_tasa = id_entidad_tasa;
    }

    public Tasa_interes getTasa() {
        return tasa;
    }

    public void setTasa(Tasa_interes tasa) {
        this.tasa = tasa;
    }

    public Entidad_financiera getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad_financiera entidad) {
        this.entidad = entidad;
    }
}