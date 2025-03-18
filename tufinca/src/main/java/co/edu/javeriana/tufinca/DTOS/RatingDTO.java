package co.edu.javeriana.tufinca.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {
    private Long id;
    private String nombreUsuario;
    private String nombrePropiedad;
    private int calificacion;
    private String comentario;
}
