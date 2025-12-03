package pe.edu.upc.inmobivalor_be.serviceinterfaces;

import pe.edu.upc.inmobivalor_be.entities.CreditoPrestamo;
import java.util.List;

public interface ICreditoPrestamoService {
    public List<CreditoPrestamo> listarCreditoPrestamo();
    public void insert(CreditoPrestamo creditoPrestamo);
    public List<CreditoPrestamo> listarPorEntidad(Integer idEntidad);
}
