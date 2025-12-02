package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.dtos.*;
import pe.edu.upc.inmobivalor_be.entities.Cliente;
import pe.edu.upc.inmobivalor_be.entities.Usuario;
import pe.edu.upc.inmobivalor_be.repositories.IClienteRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IClienteService;

import java.util.List;

@Service
public class ClienteServiceImplement implements IClienteService {
    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
    @Override
    public void insert(Cliente cliente) {
        clienteRepository.save(cliente);
    }


    @Override
    public List<ClienteByNombreDTO> ClienteByNombre(String name) {
        return clienteRepository.ClienteByNombre(name);
    }

    @Override
    public List<ClienteByCorreoDTO> ClienteByCorreo(String correo1) {
        return clienteRepository.ClienteByCorreo(correo1);
    }

    @Override
    public List<ClienteActivosDTO> ClienteActivos() {
        return clienteRepository.ClienteActivos();
    }

    @Override
    public List<ClienteBonoAptoDTO> ClienteBonoApto() {
        return clienteRepository.ClienteBonoApto();
    }

    @Override
    public List<BusquedaClienteDTO> BusquedaCliente(String busqueda) {
        return clienteRepository.BusquedaCliente(busqueda);
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        if(clienteRepository.findById(cliente.getId_cliente()).isPresent()) {
            return clienteRepository.save(cliente);
        }
        return null;
    }
    @Override
    public void update(Cliente cliente) {
        clienteRepository.save(cliente);
    }
    @Override
    public Cliente searchid(int id ) {
        return clienteRepository.findById(id).orElse(new Cliente());
    }

}
