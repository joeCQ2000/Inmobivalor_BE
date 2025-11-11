package pe.edu.upc.inmobivalor_be.serviceinterfaces;

import pe.edu.upc.inmobivalor_be.entities.Cambio_moneda;
import pe.edu.upc.inmobivalor_be.entities.Moneda;

import java.util.List;

public interface ICambio_monedaService {
    public List<Cambio_moneda> listarcambiomoneda();
    public void insert(Cambio_moneda cambio_moneda);
}
