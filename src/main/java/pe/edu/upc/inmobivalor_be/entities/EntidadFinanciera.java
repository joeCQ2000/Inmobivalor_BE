package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "entidad_financiera")
public class EntidadFinanciera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_entidad_financiera;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "ruc", nullable = false, length = 150)
    private String ruc;

    @Column(name = "direccion", nullable = false, length = 150)
    private String direccion;

    @Column(name = "telefono", nullable = false, length = 150)
    private String telefono;

    @Column(name = "correo", nullable = false, length = 150)
    private String correo;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_entidad_financiera")
    private EntidadFinanciera entidad_financiera;

    public EntidadFinanciera() {

    }

    public EntidadFinanciera(int id_entidad_financiera, String nombre, String ruc, String direccion, String telefono, String correo, EntidadFinanciera entidad_financiera) {
        this.id_entidad_financiera = id_entidad_financiera;
        this.nombre = nombre;
        this.ruc = ruc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.entidad_financiera = entidad_financiera;
    }

    public int getId_entidad_financiera() {
        return id_entidad_financiera;
    }

    public void setId_entidad_financiera(int id_entidad_financiera) {
        this.id_entidad_financiera = id_entidad_financiera;
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

    public EntidadFinanciera getEntidad_financiera() {
        return entidad_financiera;
    }

    public void setEntidad_financiera(EntidadFinanciera entidad_financiera) {
        this.entidad_financiera = entidad_financiera;
    }
}
