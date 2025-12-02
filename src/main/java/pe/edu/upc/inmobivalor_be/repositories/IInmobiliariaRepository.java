package pe.edu.upc.inmobivalor_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.inmobivalor_be.entities.Inmobiliaria;

import java.util.List;

public interface IInmobiliariaRepository extends JpaRepository<Inmobiliaria, Integer> {


    @Query("""
       SELECT i
       FROM Inmobiliaria i
       WHERE (:estado IS NULL OR i.estado = :estado)
         AND (:situacion IS NULL OR i.situacion_inmobiliaria = :situacion)
         AND (:ubicacion IS NULL OR i.ubicacion = :ubicacion)
       """)
    List<Inmobiliaria> buscarInmobiliarias(
            @Param("estado") Boolean estado,
            @Param("situacion") String situacion
            , @Param("ubicacion") String ubicacion
    );

}
