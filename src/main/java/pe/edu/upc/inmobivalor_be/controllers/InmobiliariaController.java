package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.InmobiliariaDTO;
import pe.edu.upc.inmobivalor_be.entities.Inmobiliaria;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IInmobiliariaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inmobiliaria")
public class InmobiliariaController {
    @Autowired
    private IInmobiliariaService iInmobiliariaService;

    @GetMapping("/listar")
    public List<Inmobiliaria> listarInmobiliaria() {
        return iInmobiliariaService.listarInmobiliarias().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Inmobiliaria.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/registrar")
    public void registrar (@RequestBody InmobiliariaDTO inmobiliariaDTO) {
        ModelMapper m = new ModelMapper();
        Inmobiliaria inmobiliaria = m.map(inmobiliariaDTO, Inmobiliaria.class);
        iInmobiliariaService.insert(inmobiliaria);
    }
}
