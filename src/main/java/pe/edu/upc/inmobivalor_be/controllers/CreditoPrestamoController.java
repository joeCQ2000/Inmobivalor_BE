package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.CreditoPrestamoDTO;
import pe.edu.upc.inmobivalor_be.entities.CreditoPrestamo;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ICreditoPrestamoService;

import java.util.List;

@RestController
@RequestMapping("/credito")
public class CreditoPrestamoController {

    @Autowired
    private ICreditoPrestamoService creditoPrestamoService;

    @GetMapping("/listar")
    public List<CreditoPrestamo> listarCreditoPrestamo() {
        return creditoPrestamoService.listarCreditoPrestamo();
    }

    @PostMapping("/registrar")
    public void registrar(@RequestBody CreditoPrestamoDTO creditoPrestamoDTO) {
        ModelMapper m = new ModelMapper();
        CreditoPrestamo creditoPrestamo = m.map(creditoPrestamoDTO, CreditoPrestamo.class);
        creditoPrestamoService.insert(creditoPrestamo);
    }
}
