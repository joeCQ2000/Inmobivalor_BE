package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.UsuarioDTO;
import pe.edu.upc.inmobivalor_be.entities.Usuario;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IUsuarioService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping ("/listar")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuario().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Usuario.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/registrar")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    public void registrar (@RequestBody UsuarioDTO usuarioDTO) {
        ModelMapper m = new ModelMapper();
        Usuario usuario = m.map(usuarioDTO, Usuario.class);
        usuarioService.insert(usuario);
    }

    @PutMapping("/actualizar")
    public void actualizar(@RequestBody UsuarioDTO dto) {
        ModelMapper m = new ModelMapper();
        Usuario usuario = m.map(dto, Usuario.class);
        usuarioService.update(usuario);
    }
    @GetMapping("/{id}")
    public UsuarioDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        UsuarioDTO dto = m.map(usuarioService.searchid(id), UsuarioDTO.class);
        return dto;
    }


}
