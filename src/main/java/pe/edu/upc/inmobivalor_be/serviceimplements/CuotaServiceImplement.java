package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.entities.Cuota;
import pe.edu.upc.inmobivalor_be.repositories.ICuotaRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ICuotaService;

import java.util.List;

@Service
public class CuotaServiceImplement implements ICuotaService {

    @Autowired
    private ICuotaRepository cuotaRepository;

    @Override
    public List<Cuota> listacuotas() {
        return cuotaRepository.findAll();
    }

    @Override
    public void insert(Cuota cuota) {
        cuotaRepository.save(cuota);
    }
}
