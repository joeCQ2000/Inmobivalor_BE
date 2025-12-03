package pe.edu.upc.inmobivalor_be.serviceinterfaces;

import pe.edu.upc.inmobivalor_be.entities.Cliente;
import pe.edu.upc.inmobivalor_be.entities.Tasa_interes;

import java.util.List;

public interface ITasa_interesService {
    public List<Tasa_interes> listartasas();
    public void insert(Tasa_interes tasa_interes);
    public void update(Tasa_interes tasa_interes);
    public void delete(int id); // Eliminación lógica
    public Tasa_interes findById(int id);
}
