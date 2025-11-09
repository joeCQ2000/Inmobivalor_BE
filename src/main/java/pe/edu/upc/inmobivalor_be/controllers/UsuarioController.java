package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.ClienteDTO;
import pe.edu.upc.inmobivalor_be.dtos.UsuarioDTO;
import pe.edu.upc.inmobivalor_be.entities.Cliente;
import pe.edu.upc.inmobivalor_be.entities.Usuario;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IClienteService;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IUsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping ("/listar")
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuario().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Usuario.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/registrar")
    public void registrar (@RequestBody UsuarioDTO usuarioDTO) {
        ModelMapper m = new ModelMapper();
        Usuario usuario = m.map(usuarioDTO, Usuario.class);
        usuarioService.insert(usuario);
    }


}
