package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.entities.Cambio_moneda;
import pe.edu.upc.inmobivalor_be.entities.Moneda;
import pe.edu.upc.inmobivalor_be.repositories.ICambio_monedaRepository;
import pe.edu.upc.inmobivalor_be.repositories.IMonedaRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ICambio_monedaService;

import java.util.List;

@Service
public class Cambio_monedaServiceImplement implements ICambio_monedaService {
    @Autowired
    private ICambio_monedaRepository cambio_monedaRepository;
    @Override
    public List<Cambio_moneda> listarcambiomoneda(){
        return cambio_monedaRepository.findAll();
    }
    @Override
    public void insert (Cambio_moneda cambio_moneda) {
        cambio_monedaRepository.save(cambio_moneda);
    }
}
