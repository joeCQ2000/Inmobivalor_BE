package pe.edu.upc.inmobivalor_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.inmobivalor_be.entities.Inmobiliaria;

public interface IInmobiliariaRepository extends JpaRepository<Inmobiliaria, Integer> {
}
