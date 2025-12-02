package pe.edu.upc.inmobivalor_be.serviceinterfaces;

import pe.edu.upc.inmobivalor_be.dtos.DatosCronogramaDTO;
import pe.edu.upc.inmobivalor_be.dtos.SimulacionFrancesResponseDTO;

import java.util.List;

public interface ISimuladorFinancieroService {

    SimulacionFrancesResponseDTO simularFrances(DatosCronogramaDTO datos);

    // Nuevo: varios planes de pago en un solo request
    List<SimulacionFrancesResponseDTO> simularFrancesBatch(List<DatosCronogramaDTO> listaDatos);
}
