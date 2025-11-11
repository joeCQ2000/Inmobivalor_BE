package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.entities.CreditoPrestamo;
import pe.edu.upc.inmobivalor_be.repositories.ICreditoPrestamoRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.ICreditoPrestamoService;

import java.util.List;

@Service
public class CreditoPrestamoServiceImplement implements ICreditoPrestamoService {
    @Autowired
    private ICreditoPrestamoRepository iCreditoPrestamoRepository;

    @Override
    public List<CreditoPrestamo> listarCreditoPrestamo() {
        return iCreditoPrestamoRepository.findAll();
    }
    @Override
    public void insert(CreditoPrestamo creditoPrestamo) {
        iCreditoPrestamoRepository.save(creditoPrestamo);
    }
}


