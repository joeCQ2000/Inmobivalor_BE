package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.MonedaDTO;
import pe.edu.upc.inmobivalor_be.entities.Moneda;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IMonedaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/moneda")
public class MonedaController {
    @Autowired
    private IMonedaService monedaService;

    @GetMapping("/registrar")public List<Moneda> listarmoneda() {
        return monedaService.listarmonedas().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Moneda.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/listar")
    public void registrar (@RequestBody MonedaDTO monedaDTO) {
        ModelMapper m = new ModelMapper();
        Moneda moneda = m.map(monedaDTO, Moneda.class);
        monedaService.insert(moneda);
    }
}
