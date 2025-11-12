package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.ClienteDTO;
import pe.edu.upc.inmobivalor_be.entities.Cliente;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IClienteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private IClienteService clienteService;

    @GetMapping("/listar")
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Cliente.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/registrar")
    public void registrar (@RequestBody ClienteDTO clienteDTO) {
        ModelMapper m = new ModelMapper();
        Cliente cliente = m.map(clienteDTO, Cliente.class);
        clienteService.insert(cliente);
    }

}