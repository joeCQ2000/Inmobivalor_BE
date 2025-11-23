package pe.edu.upc.inmobivalor_be.controllers;

import jakarta.persistence.Column;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.RolDTO;
import pe.edu.upc.inmobivalor_be.dtos.Tasa_interesDTO;
import pe.edu.upc.inmobivalor_be.entities.Rol;
import pe.edu.upc.inmobivalor_be.entities.Tasa_interes;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IRolService;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ITasa_interesService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasa_interes")
public class Tasa_interesController {

    @Autowired
    private ITasa_interesService tasa_interesService;

    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public List<Tasa_interes> listartasainteres() {
        return tasa_interesService.listartasas().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Tasa_interes.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/registrar")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public void registrar (@RequestBody Tasa_interesDTO tasa_interesDTO) {
        ModelMapper m = new ModelMapper();
        Tasa_interes tasa_interes = m.map(tasa_interesDTO, Tasa_interes.class);
        tasa_interesService.insert(tasa_interes);
    }
}
