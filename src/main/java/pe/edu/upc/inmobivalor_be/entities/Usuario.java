package pe.edu.upc.inmobivalor_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;

    @Column(name = "contrasenha", length = 200)
    private String contrasenha;

    @Column(name = "username", length = 100, unique = true)
    private String username;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "correo", nullable = false, length = 50)
    private String correo;


    @Column(name = "dni", nullable = false, length = 8)
    private String dni;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;

    @Column(name= "otp_code")
    private String otpCode;

    @Column(name= "otp_expiry")
    private LocalDateTime otpExpiry;

    @Column(name="otp_required")
    private Boolean otpRequired = false;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Rol> roles;

    public Usuario() {}

    public Usuario(int id_usuario, String contrasenha, String username, String nombres, String apellidos, String correo, String dni, boolean estado, String telefono, String otpCode, LocalDateTime otpExpiry, Boolean otpRequired, List<Rol> roles) {
        this.id_usuario = id_usuario;
        this.contrasenha = contrasenha;
        this.username = username;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.dni = dni;
        this.estado = estado;
        this.telefono = telefono;
        this.otpCode = otpCode;
        this.otpExpiry = otpExpiry;
        this.otpRequired = otpRequired;
        this.roles = roles;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public LocalDateTime getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(LocalDateTime otpExpiry) {
        this.otpExpiry = otpExpiry;
    }

    public Boolean getOtpRequired() {
        return otpRequired;
    }

    public void setOtpRequired(Boolean otpRequired) {
        this.otpRequired = otpRequired;
    }
}