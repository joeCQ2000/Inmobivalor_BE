package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.entities.Moneda;
import pe.edu.upc.inmobivalor_be.repositories.IMonedaRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IMonedaService;

import java.util.List;

@Service

public class MonedaServiceImplement implements IMonedaService {
    @Autowired
    private IMonedaRepository monedaRepository;
    @Override
    public List<Moneda> listarmonedas(){
        return monedaRepository.findAll();
    }
    @Override
    public void insert (Moneda moneda) {
        monedaRepository.save(moneda);
    }
}
