package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.*;
import pe.edu.upc.inmobivalor_be.entities.Cliente;
import pe.edu.upc.inmobivalor_be.repositories.IClienteRepository;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IClienteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IClienteRepository clienteRepository;

    @GetMapping("/listar")
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Cliente.class);
        }).collect(Collectors.toList());
    }

    @PostMapping("/registrar")
    public void registrar(@RequestBody ClienteDTO clienteDTO) {
        ModelMapper m = new ModelMapper();
        Cliente cliente = m.map(clienteDTO, Cliente.class);
        clienteService.insert(cliente);
    }


    @GetMapping("clientebynombre/{name}")
    public List<ClienteByNombreDTO> ClienteByNombre(@PathVariable String name) {
        return clienteRepository.ClienteByNombre(name);
    }

    @GetMapping("clientebycorreo/{correo1}")
    public List<ClienteByCorreoDTO> ClienteByCorreo(@PathVariable String correo1) {
        return clienteRepository.ClienteByCorreo(correo1);
    }

    @GetMapping("clienteactivos")
    public List<ClienteActivosDTO> ClienteActivos() {
        return clienteRepository.ClienteActivos();
    }
    @GetMapping("clientebonoaptos")
    public List<ClienteBonoAptoDTO> ClienteBonoApto() {
        return clienteRepository.ClienteBonoApto();
    }

    @GetMapping("busquedacliente/{busqueda}")
    public List<BusquedaClienteDTO> BusquedaCliente(@PathVariable String busqueda) {
        return clienteRepository.BusquedaCliente(busqueda);
    }

    @PutMapping("/cliente")
    public ResponseEntity<ClienteDTO> actualizarCliente(@RequestBody ClienteDTO clienteDTO) {
        ModelMapper mapper = new ModelMapper();
        try{
            Cliente cliente =mapper.map(clienteDTO,Cliente.class);
            cliente=clienteService.actualizarCliente(cliente);
            clienteDTO =mapper.map(cliente,ClienteDTO.class);
        }catch(Exception e){
            return new ResponseEntity<>(clienteDTO, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(clienteDTO);
    }
}