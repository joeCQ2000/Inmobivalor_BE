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
}
