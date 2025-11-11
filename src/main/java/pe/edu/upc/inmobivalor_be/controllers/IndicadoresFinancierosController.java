package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.entities.IndicadoresFinancieros;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IIndicadoresFinancierosService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/indicadores")
public class IndicadoresFinancierosController {
    @Autowired
    private IIndicadoresFinancierosService indicadoresFinancierosService;

    @GetMapping("/registrar")
    public List<IndicadoresFinancieros> listarCreditoPrestamo() {
        return indicadoresFinancierosService.listarIndicadoresFinancieros().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, IndicadoresFinancieros.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/listar")
    public void registrar (@RequestBody IndicadoresFinancieros indicadoresFinancierosDTO) {
        ModelMapper m = new ModelMapper();
        IndicadoresFinancieros indicadoresFinancieros = m.map(indicadoresFinancierosDTO, IndicadoresFinancieros.class);
        indicadoresFinancierosService.insert(indicadoresFinancieros);
    }
}
