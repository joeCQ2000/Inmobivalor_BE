package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.entities.Inmobiliaria;
import pe.edu.upc.inmobivalor_be.repositories.IInmobiliariaRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IInmobiliariaService;

import java.util.List;

@Service
public class InmobiliariaServiceImplement implements IInmobiliariaService {
    @Autowired
    private IInmobiliariaRepository inmobiliariaRepository;

    @Override
    public List<Inmobiliaria> listarInmobiliarias() {
        return inmobiliariaRepository.findAll();
    }
    @Override
    public void insert(Inmobiliaria inmobiliaria) {
        inmobiliariaRepository.save(inmobiliaria);
    }

    @Override
    public Inmobiliaria searchid(int id) {
        return inmobiliariaRepository.findById(id).orElse(new Inmobiliaria());
    }

    @Override
    public List<Inmobiliaria> buscarInmobiliarias(Boolean estado, String situacion_inmobiliaria, String ubicacion) {
        if (situacion_inmobiliaria != null && situacion_inmobiliaria.isBlank()) {
            situacion_inmobiliaria = null;
        }
        if (ubicacion != null && ubicacion.isBlank()) {
            ubicacion = null;
        }

        return inmobiliariaRepository.buscarInmobiliarias(
                estado,
                situacion_inmobiliaria,
                ubicacion
        );
    }


}



