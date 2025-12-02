package pe.edu.upc.inmobivalor_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.inmobivalor_be.entities.CreditoPrestamo;

import java.util.List;

public interface ICreditoPrestamoRepository extends JpaRepository<CreditoPrestamo, Integer>{
    @Query(
            value = "SELECT cp.* " +
                    "FROM credito_prestamo cp " +
                    "JOIN entidad_financiera ef ON cp.id_entidad = ef.id_entidad " +
                    "WHERE ef.id_entidad = :idEntidad",
            nativeQuery = true
    )
    List<CreditoPrestamo> listarPorEntidad(@Param("idEntidad") Integer idEntidad);
}

