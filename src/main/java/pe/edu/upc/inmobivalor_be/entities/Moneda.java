package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "moneda")
public class Moneda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_moneda;

    @Column(name = "estado", nullable = false , length = 50 )
    private boolean estado;

    @Column(name = "tipo_moneda", nullable = false , length = 20 )
    private String tipo_moneda;

    public  Moneda () {}

    public Moneda(int id_moneda, boolean estado, String tipo_moneda) {
        this.id_moneda = id_moneda;
        this.estado = estado;
        this.tipo_moneda = tipo_moneda;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getId_moneda() {
        return id_moneda;
    }

    public void setId_moneda(int id_moneda) {
        this.id_moneda = id_moneda;
    }

    public String getTipo_moneda() {
        return tipo_moneda;
    }

    public void setTipo_moneda(String tipo_moneda) {
        this.tipo_moneda = tipo_moneda;
    }
}
