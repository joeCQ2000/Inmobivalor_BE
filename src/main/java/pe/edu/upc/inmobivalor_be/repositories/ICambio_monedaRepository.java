package pe.edu.upc.inmobivalor_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.inmobivalor_be.entities.Cambio_moneda;

public interface ICambio_monedaRepository extends JpaRepository<Cambio_moneda, Integer> {
}
