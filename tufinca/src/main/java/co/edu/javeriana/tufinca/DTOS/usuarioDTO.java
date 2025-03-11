package co.edu.javeriana.tufinca.DTOS;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class usuarioDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private String contraseña;
}
