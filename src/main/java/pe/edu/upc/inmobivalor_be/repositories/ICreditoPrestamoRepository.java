package pe.edu.upc.inmobivalor_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.inmobivalor_be.entities.CreditoPrestamo;

public interface ICreditoPrestamoRepository extends JpaRepository<CreditoPrestamo, Integer>{
}

