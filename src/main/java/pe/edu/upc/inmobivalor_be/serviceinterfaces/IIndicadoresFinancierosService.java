package pe.edu.upc.inmobivalor_be.serviceinterfaces;
import pe.edu.upc.inmobivalor_be.entities.IndicadoresFinancieros;

import java.util.List;

public interface IIndicadoresFinancierosService {
    public List<IndicadoresFinancieros> listarIndicadoresFinancieros();
    public void insert(IndicadoresFinancieros indicadoresFinancieros);
}
