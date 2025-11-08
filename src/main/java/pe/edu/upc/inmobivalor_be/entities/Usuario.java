package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "correo", nullable = false, length = 50)
    private String correo;

    @Column(name = "contrasenha", nullable = false, length = 50)
    private String contrasenha;

    @Column(name = "dni", nullable = false, length = 8)
    private String dni;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    public Usuario() {}

    public Usuario(int id_usuario, String apellidos, String nombres, String correo, String contrasenha, String dni, boolean estado, Rol rol, String telefono) {
        this.id_usuario = id_usuario;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.correo = correo;
        this.contrasenha = contrasenha;
        this.dni = dni;
        this.estado = estado;
        this.rol = rol;
        this.telefono = telefono;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
