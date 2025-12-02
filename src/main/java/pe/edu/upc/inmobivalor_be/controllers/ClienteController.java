package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.ClienteDTO;
import pe.edu.upc.inmobivalor_be.dtos.UsuarioDTO;
import pe.edu.upc.inmobivalor_be.entities.Cliente;
import pe.edu.upc.inmobivalor_be.entities.Usuario;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IClienteService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private IClienteService clienteService;

    @GetMapping("/listar")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Cliente.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/registrar")
    //@PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    public void registrar (@RequestBody ClienteDTO clienteDTO) {
        ModelMapper m = new ModelMapper();
        Cliente cliente = m.map(clienteDTO, Cliente.class);
        clienteService.insert(cliente);
    }
    @PutMapping("/actualizar")
    public void actualizar(@RequestBody ClienteDTO dto) {
        ModelMapper m = new ModelMapper();
        Cliente cliente = m.map(dto, Cliente.class);
        clienteService.update(cliente);
    }
    @GetMapping("/{id}")
    public ClienteDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        ClienteDTO dto = m.map(clienteService.searchid(id), ClienteDTO.class);
        return dto;
    }
}