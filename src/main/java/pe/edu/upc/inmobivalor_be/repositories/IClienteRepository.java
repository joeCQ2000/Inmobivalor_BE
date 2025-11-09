package pe.edu.upc.inmobivalor_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.inmobivalor_be.entities.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
}
