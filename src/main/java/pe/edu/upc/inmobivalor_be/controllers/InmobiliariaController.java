package pe.edu.upc.inmobivalor_be.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.inmobivalor_be.dtos.InmobiliariaDTO;
import pe.edu.upc.inmobivalor_be.dtos.UsuarioDTO;
import pe.edu.upc.inmobivalor_be.entities.Inmobiliaria;
import pe.edu.upc.inmobivalor_be.entities.Usuario;
import pe.edu.upc.inmobivalor_be.serviceinterfaces.IInmobiliariaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inmobiliaria")
public class InmobiliariaController {
    @Autowired
    private IInmobiliariaService iInmobiliariaService;

    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public List<Inmobiliaria> listarInmobiliaria() {
        return iInmobiliariaService.listarInmobiliarias().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, Inmobiliaria.class);
        }).collect(Collectors.toList());
    }
    @PostMapping("/registrar")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
    public void registrar (@RequestBody InmobiliariaDTO inmobiliariaDTO) {

        ModelMapper m = new ModelMapper();
        Inmobiliaria inmobiliaria = m.map(inmobiliariaDTO, Inmobiliaria.class);

        iInmobiliariaService.insert(inmobiliaria);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> actualizar(
            @PathVariable("id") int id,
            @RequestBody InmobiliariaDTO dto
    ) {
        // 1. Buscar la inmobiliaria existente
        Inmobiliaria existente = iInmobiliariaService.searchid(id);
        // Ojo: depende de tu implementación, ajusta esta validación
        if (existente == null || existente.getId_inmobiliaria() == 0) {
            return ResponseEntity.notFound().build();
        }

        // 2. Actualizar campos desde el DTO
        existente.setUbicacion(dto.getUbicacion());
        existente.setArea(dto.getArea());
        existente.setPrecio(dto.getPrecio());
        existente.setDescripcion(dto.getDescripcion());
        existente.setSituacion_inmobiliaria(dto.getSituacion_inmobiliaria());
        existente.setEstado(dto.getEstado());
        existente.setImagen(dto.getImagen()); // id de la imagen (nuevo o existente)

        // 3. Guardar (save => UPDATE porque ya tiene id)
        iInmobiliariaService.insert(existente);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public InmobiliariaDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        InmobiliariaDTO dto = m.map(iInmobiliariaService.searchid(id), InmobiliariaDTO.class);
        return dto;
    }


    @GetMapping("/buscar")
    public ResponseEntity<List<Inmobiliaria>> buscar(
            @RequestParam(required = false) Boolean estado,
            @RequestParam(name = "situacion_inmobiliaria", required = false) String situacion_inmobiliaria,
            @RequestParam(required = false) String ubicacion
    ) {
        List<Inmobiliaria> resultado =
                iInmobiliariaService.buscarInmobiliarias(estado, situacion_inmobiliaria, ubicacion);
        return ResponseEntity.ok(resultado);
    }

}

//@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR_FINANCIERO')")
