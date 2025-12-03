package pe.edu.upc.inmobivalor_be.serviceinterfaces;

import pe.edu.upc.inmobivalor_be.dtos.IndicadoresFinancierosDTO;
import pe.edu.upc.inmobivalor_be.entities.IndicadoresFinancieros;

import java.util.List;

public interface IIndicadoresFinancierosService {

    List<IndicadoresFinancieros> listarIndicadoresFinancieros();

    void insert(IndicadoresFinancierosDTO indicadoresFinancierosDTO);
}
