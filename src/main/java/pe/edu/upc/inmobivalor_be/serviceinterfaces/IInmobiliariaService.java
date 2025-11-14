package pe.edu.upc.inmobivalor_be.serviceinterfaces;
import pe.edu.upc.inmobivalor_be.entities.Inmobiliaria;

import java.util.List;

public interface IInmobiliariaService {
    public List<Inmobiliaria> listarInmobiliarias();
    public void insert(Inmobiliaria inmobiliaria);
}
