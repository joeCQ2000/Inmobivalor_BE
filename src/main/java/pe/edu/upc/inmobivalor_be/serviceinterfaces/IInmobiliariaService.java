package pe.edu.upc.inmobivalor_be.serviceinterfaces;
import pe.edu.upc.inmobivalor_be.entities.Inmobiliaria;
import pe.edu.upc.inmobivalor_be.entities.Usuario;

import java.util.List;

public interface IInmobiliariaService {
    public List<Inmobiliaria> listarInmobiliarias();
    public void insert(Inmobiliaria inmobiliaria);
    public Inmobiliaria searchid(int id);

    public List<Inmobiliaria> buscarInmobiliarias(Boolean estado,
                                                  String situacion_inmobiliaria, String ubicacion);

}
