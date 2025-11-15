package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "entidad_financiera")
public class Entidad_financiera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_entidad;

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

    // Relación ManyToMany con Tasa_interes
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "entidad_tasa",
            joinColumns = @JoinColumn(name = "id_entidad"),
            inverseJoinColumns = @JoinColumn(name = "id_tasa")
    )
    private Set<Tasa_interes> tasas = new HashSet<>();

    public Entidad_financiera() {}

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

    public Set<Tasa_interes> getTasas() {
        return tasas;
    }

    public void setTasas(Set<Tasa_interes> tasas) {
        this.tasas = tasas;
    }

    public void addTasa(Tasa_interes tasa) {
        this.tasas.add(tasa);
    }

    public void removeTasa(Tasa_interes tasa) {
        this.tasas.remove(tasa);
    }
}
