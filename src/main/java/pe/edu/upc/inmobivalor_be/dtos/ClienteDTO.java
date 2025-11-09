package pe.edu.upc.inmobivalor_be.dtos;

import jakarta.persistence.Column;
import pe.edu.upc.inmobivalor_be.entities.Usuario;

public class ClienteDTO {
    private int id_cliente;


    private String nombres;
    private String apellidos;

    private String correo;


    private String telefono;

    private String dni;

    private boolean es_activo;

    private boolean aplica_bono;

    private Usuario usuario;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public boolean isAplica_bono() {
        return aplica_bono;
    }

    public void setAplica_bono(boolean aplica_bono) {
        this.aplica_bono = aplica_bono;
    }

    public boolean isEs_activo() {
        return es_activo;
    }

    public void setEs_activo(boolean es_activo) {
        this.es_activo = es_activo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
