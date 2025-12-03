package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.IndicadoresFinancierosDTO;
import pe.edu.upc.inmobivalor_be.entities.IndicadoresFinancieros;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IIndicadoresFinancierosService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/indicadores")
public class IndicadoresFinancierosController {

    @Autowired
    private IIndicadoresFinancierosService indicadoresFinancierosService;

    @GetMapping("/listar")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public List<IndicadoresFinancierosDTO> listarIndicadores() {
        return indicadoresFinancierosService.listarIndicadoresFinancieros()
                .stream()
                .map(x -> {
                    ModelMapper modelMapper = new ModelMapper();
                    return modelMapper.map(x, IndicadoresFinancierosDTO.class);
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/registrar")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public void registrar(@RequestBody IndicadoresFinancierosDTO dto) {
        indicadoresFinancierosService.insert(dto);
    }
}
