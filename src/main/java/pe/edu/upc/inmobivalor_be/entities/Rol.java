package pe.edu.upc.inmobivalor_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "rol", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "nombreRol"})})
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_rol;

    @Column(name = "nombreRol", nullable = false, length = 50)
    private String nombreRol;

    @Column(name = "estado", nullable = false, length = 50)
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private Usuario user;

    public Rol() {

    }

    public Rol(int id_rol, String nombreRol, boolean estado, Usuario user) {
        this.id_rol = id_rol;
        this.nombreRol = nombreRol;
        this.estado = estado;
        this.user = user;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}