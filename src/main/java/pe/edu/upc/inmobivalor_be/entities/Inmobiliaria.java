package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "inmobiliaria")
public class Inmobiliaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_inmobiliaria;

    @Column(name = "ubicacion", nullable = false, length = 100)
    private String ubicacion;

    @Column(name = "imagen")
    private Long imagen;

    @Column(name = "area", nullable = false, length = 100)
    private String area;

    @Column(name = "precio", nullable = false)
    private int precio;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "situacion_inmobiliaria", nullable = false, length = 50)
    private String situacion_inmobiliaria;

    @Column(name = "estado", nullable = false)
    private Boolean estado;


    public Inmobiliaria() {}

    public Inmobiliaria(int id_inmobiliaria, String ubicacion, Long imagen, String area, int precio,
                        String descripcion, String situacion_inmobiliaria,
                        Boolean estado, Usuario usuario) {
        this.id_inmobiliaria = id_inmobiliaria;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
        this.area = area;
        this.precio = precio;
        this.descripcion = descripcion;
        this.situacion_inmobiliaria = situacion_inmobiliaria;
        this.estado = estado;

    }

    public int getId_inmobiliaria() {
        return id_inmobiliaria;
    }

    public void setId_inmobiliaria(int id_inmobiliaria) {
        this.id_inmobiliaria = id_inmobiliaria;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Long getImagen() {
        return imagen;
    }

    public void setImagen(Long imagen) {
        this.imagen = imagen;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSituacion_inmobiliaria() {
        return situacion_inmobiliaria;
    }

    public void setSituacion_inmobiliaria(String situacion_inmobiliaria) {
        this.situacion_inmobiliaria = situacion_inmobiliaria;
    }

    public Boolean isEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }


}
