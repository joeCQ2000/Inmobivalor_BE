package pe.edu.upc.inmobivalor_be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.DatosCronogramaDTO;
import pe.edu.upc.inmobivalor_be.dtos.SimulacionFrancesResponseDTO;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ISimuladorFinancieroService;

@RestController
@RequestMapping("/simulador")
public class SimuladorFinancieroController {

    @Autowired
    private ISimuladorFinancieroService simuladorService;

    @PostMapping("/frances")
    public SimulacionFrancesResponseDTO simularFrances(@RequestBody DatosCronogramaDTO datos) {
        return simuladorService.simularFrances(datos);
    }
}
