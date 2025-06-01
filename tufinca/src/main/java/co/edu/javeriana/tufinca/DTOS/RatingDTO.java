package co.edu.javeriana.tufinca.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {
    private Long id;
    private Long solicitudId;
    private Long arrendadorId;
    private Long arrendatarioId;
    private String nombreUsuario;
    private String nombrePropiedad;
    private int calificacionFinca;
    private String comentarioFinca;
    private Integer calificacionArrendatario;
    private String comentarioArrendatario;
    private Integer status;
}
