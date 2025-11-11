package pe.edu.upc.inmobivalor_be.serviceinterfaces;
import pe.edu.upc.inmobivalor_be.entities.Moneda;

import java.util.List;

public interface IMonedaService {
    public List<Moneda> listarmonedas();
    public void insert(Moneda moneda);
}
