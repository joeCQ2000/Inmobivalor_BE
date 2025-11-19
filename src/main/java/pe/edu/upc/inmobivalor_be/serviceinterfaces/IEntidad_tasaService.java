package pe.edu.upc.inmobivalor_be.serviceinterfaces;

import pe.edu.upc.inmobivalor_be.entities.Entidad_tasa;

import java.util.List;

public interface IEntidad_tasaService {
    public List<Entidad_tasa> listar();
    public void insert(Entidad_tasa entidad_tasa);
}
