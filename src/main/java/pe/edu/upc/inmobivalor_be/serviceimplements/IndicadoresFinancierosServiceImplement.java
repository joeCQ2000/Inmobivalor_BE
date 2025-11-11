package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.entities.IndicadoresFinancieros;
import pe.edu.upc.inmobivalor_be.repositories.IIndicadoresFinancierosRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IIndicadoresFinancierosService;
import java.util.List;

@Service
public class IndicadoresFinancierosServiceImplement implements IIndicadoresFinancierosService {
    @Autowired
    private IIndicadoresFinancierosRepository indicadoresFinancierosRepository;

    @Override
    public List<IndicadoresFinancieros> listarIndicadoresFinancieros() {
        return indicadoresFinancierosRepository.findAll();
    }
    @Override
    public void insert(IndicadoresFinancieros indicadoresFinancieros) {
        indicadoresFinancierosRepository.save(indicadoresFinancieros);
    }

}
