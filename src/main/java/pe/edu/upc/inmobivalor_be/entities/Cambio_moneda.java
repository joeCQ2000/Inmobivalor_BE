package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cambio_moneda")
public class Cambio_moneda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cambio_moneda;

    @Column(name = "tasa_cambio", nullable = false , length = 50 )
    private String tasa_cambio;

    @Column(name = "estado", nullable = false , length = 50 )
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_moneda")
    private Moneda moneda;

    public Cambio_moneda() {}

    public Cambio_moneda(int id_cambio_moneda, String tasa_cambio, boolean estado, Moneda moneda) {
        this.id_cambio_moneda = id_cambio_moneda;
        this.tasa_cambio = tasa_cambio;
        this.estado = estado;
        this.moneda = moneda;
    }

    public int getId_cambio_moneda() {
        return id_cambio_moneda;
    }

    public void setId_cambio_moneda(int id_cambio_moneda) {
        this.id_cambio_moneda = id_cambio_moneda;
    }

    public String getTasa_cambio() {
        return tasa_cambio;
    }

    public void setTasa_cambio(String tasa_cambio) {
        this.tasa_cambio = tasa_cambio;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }
}
