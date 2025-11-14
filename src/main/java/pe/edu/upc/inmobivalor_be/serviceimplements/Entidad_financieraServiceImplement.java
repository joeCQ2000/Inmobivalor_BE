package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.entities.Entidad_financiera;
import pe.edu.upc.inmobivalor_be.repositories.IEntidad_financieraRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IEntidad_financieraService;

import java.util.List;

@Service
public class Entidad_financieraServiceImplement implements IEntidad_financieraService {
    @Autowired
    private IEntidad_financieraRepository entidad_financieraRepository;
    @Override
    public List<Entidad_financiera> listarentidades(){
        return entidad_financieraRepository.findAll();
    }
    @Override
    public void insert (Entidad_financiera entidad_financiera) {
        entidad_financieraRepository.save(entidad_financiera);
    }
}
