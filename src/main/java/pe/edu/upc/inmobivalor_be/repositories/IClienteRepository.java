package pe.edu.upc.inmobivalor_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.inmobivalor_be.dtos.*;
import pe.edu.upc.inmobivalor_be.entities.Cliente;

import java.util.List;



public interface IClienteRepository extends JpaRepository<Cliente, Integer> {


    //Búsqueda de cliente por nombre
    @Query("select new pe.edu.upc.inmobivalor_be.dtos.ClienteByNombreDTO(c.id_cliente,c.nombres,c.apellidos,c.correo,c.telefono,c.dni,c.es_activo,c.aplica_bono) " +
            "from Cliente c where c.nombres like concat('%',:name,'%')")
    public List<ClienteByNombreDTO> ClienteByNombre(@Param("name")String name);

    //Búsqueda de cliente por correo
    @Query("select new pe.edu.upc.inmobivalor_be.dtos.ClienteByCorreoDTO(c.id_cliente,c.nombres,c.apellidos,c.correo,c.telefono,c.dni,c.es_activo,c.aplica_bono) " +
            "from Cliente c where c.correo like concat('%',:correo1,'%')")
    public List<ClienteByCorreoDTO> ClienteByCorreo(@Param("correo1")String correo1);

    //Búsquede de los clientes Activos
    @Query("select new pe.edu.upc.inmobivalor_be.dtos.ClienteActivosDTO(c.id_cliente,c.nombres,c.apellidos,c.correo,c.telefono,c.dni,c.es_activo,c.aplica_bono) " +
            "from Cliente c where c.es_activo = true")
    public List<ClienteActivosDTO> ClienteActivos();

    //Búsqueda de los clientes que aplican al bono
    @Query("select new pe.edu.upc.inmobivalor_be.dtos.ClienteBonoAptoDTO(c.id_cliente,c.nombres,c.apellidos,c.correo,c.telefono,c.dni,c.es_activo,c.aplica_bono) " +
            "from Cliente c where c.aplica_bono = true")
    public List<ClienteBonoAptoDTO> ClienteBonoApto();

//Busqueda de cliente por nombre, dni, correo y apellido
    @Query("select new pe.edu.upc.inmobivalor_be.dtos.BusquedaClienteDTO(c.id_cliente,c.nombres,c.apellidos,c.correo,c.telefono,c.dni,c.es_activo,c.aplica_bono) " +
            "from Cliente c where c.dni like concat('%',:busqueda,'%') or c.nombres like concat('%',:busqueda,'%') or c.correo like concat('%',:busqueda,'%') or c.apellidos like concat('%',:busqueda,'%')")
    public List<BusquedaClienteDTO> BusquedaCliente(@Param("busqueda")String busqueda);

}
