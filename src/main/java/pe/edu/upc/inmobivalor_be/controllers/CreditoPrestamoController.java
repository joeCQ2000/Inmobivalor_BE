package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.CreditoComboDTO;
import pe.edu.upc.inmobivalor_be.dtos.CreditoPrestamoDTO;
import pe.edu.upc.inmobivalor_be.entities.CreditoPrestamo;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ICreditoPrestamoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/credito")
public class CreditoPrestamoController {
    @Autowired
    private ICreditoPrestamoService creditoPrestamoService;

    @GetMapping("/listar")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public List<CreditoPrestamo> listarCreditoPrestamo() {
        return creditoPrestamoService.listarCreditoPrestamo().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, CreditoPrestamo.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/registrar")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public void registrar (@RequestBody CreditoPrestamoDTO creditoPrestamoDTO) {
        ModelMapper m = new ModelMapper();
        CreditoPrestamo creditoPrestamo = m.map(creditoPrestamoDTO, CreditoPrestamo.class);
        creditoPrestamoService.insert(creditoPrestamo);
    }
    @GetMapping("/por-entidad/{idEntidad}")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public List<CreditoComboDTO> listarCreditosPorEntidad(@PathVariable Integer idEntidad) {

        return creditoPrestamoService.listarPorEntidad(idEntidad).stream()
                .map(credito -> {
                    CreditoComboDTO dto = new CreditoComboDTO();
                    dto.setIdEntidad(idEntidad);                 // viene del path
                    dto.setIdCredito(credito.getId_credito());   // id del crédito
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

