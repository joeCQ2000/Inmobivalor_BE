package pe.edu.upc.inmobivalor_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.inmobivalor_be.entities.Moneda;

public interface IMonedaRepository extends JpaRepository<Moneda, Integer> {
}
