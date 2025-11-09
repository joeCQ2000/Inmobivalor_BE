package pe.edu.upc.inmobivalor_be.serviceinterfaces;

import pe.edu.upc.inmobivalor_be.entities.Cliente;
import pe.edu.upc.inmobivalor_be.entities.Usuario;

import java.util.List;

public interface IUsuarioService {
        public List<Usuario> listarUsuario();
        public void insert(Usuario usuario);
}

