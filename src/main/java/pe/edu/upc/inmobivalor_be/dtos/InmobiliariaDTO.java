package pe.edu.upc.inmobivalor_be.dtos;

import pe.edu.upc.inmobivalor_be.entities.Usuario;

public class InmobiliariaDTO {
    private int id_inmobiliaria;
    private String ubicacion;
    private Long imagen;
    private String area;
    private int precio;
    private String descripcion;
    private String situacion_inmobiliaria;
    private Boolean estado;


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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
