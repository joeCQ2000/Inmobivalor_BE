package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.Cambio_monedaDTO;
import pe.edu.upc.inmobivalor_be.entities.Cambio_moneda;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ICambio_monedaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cambio_moneda")
public class Cambio_monedaController {
    @Autowired
    private ICambio_monedaService cambio_monedaService;

    @GetMapping("/listar")public List<Cambio_moneda> listarcambiomoneda() {
        return cambio_monedaService.listarcambiomoneda().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Cambio_moneda.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/registrar")
    public void registrar (@RequestBody Cambio_monedaDTO cambio_monedaDTO) {
        ModelMapper m = new ModelMapper();
        Cambio_moneda cambio_moneda = m.map(cambio_monedaDTO, Cambio_moneda.class);
        cambio_monedaService.insert(cambio_moneda);
    }

}
