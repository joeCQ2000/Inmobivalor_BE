package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.entities.Entidad_tasa;
import pe.edu.upc.inmobivalor_be.repositories.IEntidad_tasaRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IEntidad_tasaService;

import java.util.List;

@Service
public class Entidad_tasaServiceImplement implements IEntidad_tasaService {

    @Autowired
    private IEntidad_tasaRepository entidad_tasaRepository;

    @Override
    public List<Entidad_tasa> listar() {
        return entidad_tasaRepository.findAll();
    }

    @Override
    public void insert(Entidad_tasa entidad_tasa) {
        entidad_tasaRepository.save(entidad_tasa);
    }
}
