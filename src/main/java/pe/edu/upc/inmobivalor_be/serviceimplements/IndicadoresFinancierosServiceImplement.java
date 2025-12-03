package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.dtos.IndicadoresFinancierosDTO;
import pe.edu.upc.inmobivalor_be.entities.CreditoPrestamo;
import pe.edu.upc.inmobivalor_be.entities.IndicadoresFinancieros;
import pe.edu.upc.inmobivalor_be.repositories.ICreditoPrestamoRepository;
import pe.edu.upc.inmobivalor_be.repositories.IIndicadoresFinancierosRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IIndicadoresFinancierosService;

import java.util.List;

@Service
public class IndicadoresFinancierosServiceImplement implements IIndicadoresFinancierosService {

    @Autowired
    private IIndicadoresFinancierosRepository indicadoresFinancierosRepository;

    @Autowired
    private ICreditoPrestamoRepository creditoPrestamoRepository;

    @Override
    public List<IndicadoresFinancieros> listarIndicadoresFinancieros() {
        return indicadoresFinancierosRepository.findAll();
    }

    @Override
    public void insert(IndicadoresFinancierosDTO dto) {

        // Buscar el crédito asociado
        CreditoPrestamo credito = creditoPrestamoRepository
                .findById(dto.getId_credito())
                .orElseThrow(() ->
                        new RuntimeException("No se encontró el crédito con id: " + dto.getId_credito()));

        // Mapear DTO → Entity
        IndicadoresFinancieros entidad = new IndicadoresFinancieros();
        entidad.setVan(dto.getVan());
        entidad.setTir(dto.getTir());
        entidad.setFecha_calculo(dto.getFecha_calculo());
        entidad.setTcea(dto.getTcea());
        entidad.setTrea(dto.getTrea());
        entidad.setId_credito(credito);

        indicadoresFinancierosRepository.save(entidad);
    }
}
