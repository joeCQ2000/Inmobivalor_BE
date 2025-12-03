package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.Entidades_financieraDTO;
import pe.edu.upc.inmobivalor_be.dtos.Tasa_interesDTO;
import pe.edu.upc.inmobivalor_be.entities.Entidad_financiera;
import pe.edu.upc.inmobivalor_be.entities.Tasa_interes;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IEntidad_financieraService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/entidad_financiera")
public class Entidad_financieraController {

    @Autowired
    private IEntidad_financieraService entidad_financieraService;

    @GetMapping("/listar")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public List<Entidad_financiera> listarentidades() {
        return entidad_financieraService.listarentidades().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Entidad_financiera.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/registrar")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public void registrar (@RequestBody Entidades_financieraDTO entidades_financieraDTO) {
        ModelMapper m = new ModelMapper();
        Entidad_financiera entidad_financiera = m.map(entidades_financieraDTO, Entidad_financiera.class);
        entidad_financieraService.insert(entidad_financiera);
    }
}
