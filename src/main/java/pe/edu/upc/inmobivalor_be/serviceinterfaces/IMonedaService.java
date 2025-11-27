package pe.edu.upc.inmobivalor_be.serviceinterfaces;
import pe.edu.upc.inmobivalor_be.entities.Moneda;

import java.util.List;

public interface IMonedaService {
    public List<Moneda> listarmonedas();
    public void insert(Moneda moneda);
    public void update(Moneda moneda);
    public void delete(int id); // Eliminación lógica
    public Moneda findById(int id);
}
