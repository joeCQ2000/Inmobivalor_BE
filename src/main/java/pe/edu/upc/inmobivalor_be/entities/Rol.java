package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_rol;

    @Column(name = "nombre", nullable = false, length = 50)
    private boolean nombre;

    @Column(name = "estado", nullable = false, length = 50)
    private boolean estado;
    public Rol() {

    }

    public Rol(int id_rol, boolean nombre, boolean estado) {
        this.id_rol = id_rol;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isNombre() {
        return nombre;
    }

    public void setNombre(boolean nombre) {
        this.nombre = nombre;
    }
}
