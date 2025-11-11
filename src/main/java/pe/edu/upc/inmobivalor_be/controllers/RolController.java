package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.RolDTO;
import pe.edu.upc.inmobivalor_be.entities.Rol;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IRolService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rol")
public class RolController {
    @Autowired
    private IRolService rolService;

    @GetMapping("/listar")
    public List<Rol> listarRol() {
        return rolService.listarol().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Rol.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/registrar")
    public void registrar (@RequestBody RolDTO rolDTO) {
        ModelMapper m = new ModelMapper();
        Rol rol = m.map(rolDTO, Rol.class);
        rolService.insert(rol);
    }
}
