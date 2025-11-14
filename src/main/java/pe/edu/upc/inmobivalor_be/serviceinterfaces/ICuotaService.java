package pe.edu.upc.inmobivalor_be.serviceinterfaces;

import pe.edu.upc.inmobivalor_be.entities.Cuota;
import java.util.List;

public interface ICuotaService {
    public List<Cuota> listacuotas();
    public void insert(Cuota cuota);
}
