package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.entities.Cliente;
import pe.edu.upc.inmobivalor_be.entities.Usuario;
import pe.edu.upc.inmobivalor_be.repositories.IClienteRepository;
import pe.edu.upc.inmobivalor_be.repositories.IUsuarioRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IUsuarioService;

import java.util.List;
@Service
public class UsuarioServiceImplement implements IUsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarUsuario() {
        return usuarioRepository.findAll();
    }
    @Override
    public void insert(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
