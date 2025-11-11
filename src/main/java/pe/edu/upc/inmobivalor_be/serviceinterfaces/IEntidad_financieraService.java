package pe.edu.upc.inmobivalor_be.serviceinterfaces;

import pe.edu.upc.inmobivalor_be.entities.Entidad_financiera;
import pe.edu.upc.inmobivalor_be.entities.Tasa_interes;

import java.util.List;

public interface IEntidad_financieraService {
    public List<Entidad_financiera> listarentidades();
    public void insert(Entidad_financiera entidad_financiera);
}
