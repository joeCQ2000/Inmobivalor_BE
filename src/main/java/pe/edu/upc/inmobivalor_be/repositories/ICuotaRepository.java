package pe.edu.upc.inmobivalor_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.inmobivalor_be.entities.Cuota;

@Repository
public interface ICuotaRepository extends JpaRepository<Cuota, Integer> {

}
