package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.CuotaDTO;
import pe.edu.upc.inmobivalor_be.entities.Cuota;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ICuotaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cuota")
public class CuotaController {
// Commit Ejemplo
    @Autowired
    private ICuotaService cuotaService;

    @GetMapping("/listar")
    public List<CuotaDTO> listacuotas() {
        return cuotaService.listacuotas().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, CuotaDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping("/registrar")
    public void registrar(@RequestBody CuotaDTO cuotaDTO) {
        ModelMapper m = new ModelMapper();
        Cuota cuota = m.map(cuotaDTO, Cuota.class);
        cuotaService.insert(cuota);
    }
}
