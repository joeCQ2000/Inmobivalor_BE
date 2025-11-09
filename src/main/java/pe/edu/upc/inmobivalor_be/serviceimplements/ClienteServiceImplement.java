package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.inmobivalor_be.entities.Cliente;
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

}
