package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cliente;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "correo", nullable = false, length = 50)
    private String correo;

    @Column(name = "contrasenha", nullable = false, length = 50)
    private String contrasenha;

    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;

    @Column(name = "dni", nullable = false, length = 8)
    private String dni;

    @Column(name = "es_activo", nullable = false)
    private boolean es_activo;

    @Column(name = "aplica_bono", nullable = false)
    private boolean aplica_bono;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    public Cliente() {}

    public Cliente(int id_cliente, String nombres, String apellidos, String telefono, String correo, String contrasenha, String dni, boolean es_activo, boolean aplica_bono, Usuario usuario) {
        this.id_cliente = id_cliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasenha = contrasenha;
        this.dni = dni;
        this.es_activo = es_activo;
        this.aplica_bono = aplica_bono;
        this.usuario = usuario;
    }

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

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
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
