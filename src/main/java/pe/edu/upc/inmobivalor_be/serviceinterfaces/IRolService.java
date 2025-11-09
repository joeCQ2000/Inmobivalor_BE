package pe.edu.upc.inmobivalor_be.serviceinterfaces;

import pe.edu.upc.inmobivalor_be.entities.Rol;
import pe.edu.upc.inmobivalor_be.entities.Usuario;

import java.util.List;

public interface IRolService {
    public List<Rol> listarol();
    public void insert(Rol rol);
}
