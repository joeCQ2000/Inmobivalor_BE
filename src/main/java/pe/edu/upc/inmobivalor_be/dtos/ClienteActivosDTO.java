package pe.edu.upc.inmobivalor_be.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteActivosDTO {
    private int id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String dni;
    private boolean estado;
    private boolean aplica_bono;
}
