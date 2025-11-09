package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.entities.Rol;
import pe.edu.upc.inmobivalor_be.entities.Usuario;
import pe.edu.upc.inmobivalor_be.repositories.IRolRepository;
import pe.edu.upc.inmobivalor_be.repositories.IUsuarioRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IRolService;

import java.util.List;

@Service
public class RolServiceImplements implements IRolService {
    @Autowired
    private IRolRepository rolRepository;

    @Override
    public List<Rol> listarol() {
        return rolRepository.findAll();
    }

    @Override
    public void insert(Rol rol) {
        rolRepository.save(rol);
    }
}
