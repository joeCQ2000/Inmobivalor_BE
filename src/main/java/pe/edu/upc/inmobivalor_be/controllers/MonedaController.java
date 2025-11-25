package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public List<Moneda> listarmoneda() {
        return monedaService.listarmonedas().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Moneda.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/registrar")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public void registrar (@RequestBody MonedaDTO monedaDTO) {
        ModelMapper m = new ModelMapper();
        Moneda moneda = m.map(monedaDTO, Moneda.class);
        monedaService.insert(moneda);
    }
}
