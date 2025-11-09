package pe.edu.upc.inmobivalor_be.serviceinterfaces;

import pe.edu.upc.inmobivalor_be.entities.Cliente;

import java.util.List;

public interface IClienteService {
    public List<Cliente> listarClientes();
    public void insert(Cliente cliente);
}
