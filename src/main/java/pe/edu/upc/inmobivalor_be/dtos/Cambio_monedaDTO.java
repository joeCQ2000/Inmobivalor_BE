package pe.edu.upc.inmobivalor_be.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pe.edu.upc.inmobivalor_be.entities.Moneda;

public class Cambio_monedaDTO {
    private int id_cambio_moneda;
    private String tasa_cambio;
    private boolean estado;
    private Moneda moneda;
}
