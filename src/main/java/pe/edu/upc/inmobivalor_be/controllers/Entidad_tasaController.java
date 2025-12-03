package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.Entidad_tasaDTO;
import pe.edu.upc.inmobivalor_be.entities.Entidad_tasa;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IEntidad_tasaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/entidad_tasa")
public class Entidad_tasaController {

    @Autowired
    private IEntidad_tasaService entidad_tasaService;

    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    public List<Entidad_tasa> listarEntidadTasa() {
        return entidad_tasaService.listar().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Entidad_tasa.class);
        }).collect(Collectors.toList());
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    public void registrar(@RequestBody Entidad_tasaDTO entidad_tasaDTO) {
        ModelMapper m = new ModelMapper();
        Entidad_tasa entidad_tasa = m.map(entidad_tasaDTO, Entidad_tasa.class);
        entidad_tasaService.insert(entidad_tasa);
    }
}
