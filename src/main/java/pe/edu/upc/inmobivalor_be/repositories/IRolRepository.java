package pe.edu.upc.inmobivalor_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.inmobivalor_be.entities.Rol;

public interface IRolRepository  extends JpaRepository<Rol, Integer> {
}
