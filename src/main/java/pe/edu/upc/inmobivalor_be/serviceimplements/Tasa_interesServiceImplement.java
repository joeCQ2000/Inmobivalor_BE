package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.entities.Rol;
import pe.edu.upc.inmobivalor_be.entities.Tasa_interes;
import pe.edu.upc.inmobivalor_be.repositories.IRolRepository;
import pe.edu.upc.inmobivalor_be.repositories.ITasa_interesRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ITasa_interesService;

import java.util.List;

@Service
public class Tasa_interesServiceImplement implements ITasa_interesService {
    @Autowired
    private ITasa_interesRepository tasa_interesRepository;

    @Override
    public List<Tasa_interes> listartasas() {
        return tasa_interesRepository.findAll();
    }

    @Override
    public void insert(Tasa_interes tasa_interes) {
        tasa_interesRepository.save(tasa_interes);
    }
    
    @Override
    public void update(Tasa_interes tasa_interes) {
        tasa_interesRepository.save(tasa_interes);
    }
    
    @Override
    public void delete(int id) {
        // Eliminación lógica: cambia el estado a false en lugar de eliminar
        Tasa_interes tasa = tasa_interesRepository.findById(id).orElse(null);
        if (tasa != null) {
            tasa.setEstado(false);
            tasa_interesRepository.save(tasa);
        }
    }
    
    @Override
    public Tasa_interes findById(int id) {
        return tasa_interesRepository.findById(id).orElse(null);
    }
}
