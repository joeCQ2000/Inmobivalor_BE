package pe.edu.upc.inmobivalor_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.inmobivalor_be.entities.Entidad_tasa;

import java.util.List;
import java.util.Optional;

public interface IEntidad_tasaRepository extends JpaRepository<Entidad_tasa, Integer> {

    @Query("SELECT et FROM Entidad_tasa et " +
            "WHERE et.entidad.id_entidad = :entidadId " +
            "AND et.tasa.estado = true")
    List<Entidad_tasa> findActivasByEntidad(@Param("entidadId") int entidadId);

    @Query("SELECT et FROM Entidad_tasa et " +
            "WHERE et.entidad.id_entidad = :entidadId " +
            "AND UPPER(et.tasa.tipo_tasa) = UPPER(:tipoTasa) " +
            "AND et.tasa.estado = true")
    Optional<Entidad_tasa> findActivaByEntidadAndTipoTasa(
            @Param("entidadId") int entidadId,
            @Param("tipoTasa") String tipoTasa
    );
}

