package co.edu.javeriana.tufinca.DTOS;

import co.edu.javeriana.tufinca.entities.Usuario.TipoUsuario;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTOs {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private TipoUsuario tipoUsuario;
    private Integer status;
}