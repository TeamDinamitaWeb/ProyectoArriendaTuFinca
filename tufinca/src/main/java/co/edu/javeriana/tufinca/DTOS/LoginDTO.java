package co.edu.javeriana.tufinca.DTOS;

import lombok.*;

@Data
@Getter
@Setter
public class LoginDTO {
    private String correo;
    private String contrasena;
}
