package co.edu.javeriana.tufinca.DTOS;

import co.edu.javeriana.tufinca.entities.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
