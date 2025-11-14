package pe.edu.upc.inmobivalor_be.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pe.edu.upc.inmobivalor_be.entities.Tasa_interes;

public class Entidad_financieraController {
    private int id_entidad;

    private String nombre;

    private String ruc;

    private String direccion;

    private String telefono;

    private String correo;

    private boolean estado;

    private Tasa_interes tasa_interes;

    public int getId_entidad() {
        return id_entidad;
    }

    public void setId_entidad(int id_entidad) {
        this.id_entidad = id_entidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Tasa_interes getTasa_interes() {
        return tasa_interes;
    }

    public void setTasa_interes(Tasa_interes tasa_interes) {
        this.tasa_interes = tasa_interes;
    }
}
