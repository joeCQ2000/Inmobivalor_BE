package pe.edu.upc.inmobivalor_be.serviceinterfaces;

import pe.edu.upc.inmobivalor_be.dtos.*;
import pe.edu.upc.inmobivalor_be.entities.Cliente;

import java.util.List;

public interface IClienteService {
    public List<Cliente> listarClientes();
    public void insert(Cliente cliente);
    public List<ClienteByNombreDTO> ClienteByNombre(String name);
    public List<ClienteByCorreoDTO>ClienteByCorreo(String correo1);
    public List<ClienteActivosDTO>ClienteActivos();
    public List<ClienteBonoAptoDTO>ClienteBonoApto();
    public List<BusquedaClienteDTO>BusquedaCliente(String busqueda);
    public Cliente actualizarCliente(Cliente cliente);


}
