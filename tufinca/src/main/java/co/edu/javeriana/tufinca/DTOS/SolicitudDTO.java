package co.edu.javeriana.tufinca.DTOS;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudDTO {
    private Long id;
    private String nombrePropiedad;
    private String nombreSolicitante;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaLlegada;
    private LocalDateTime fechaSalida;
    private double valor;
    private String estado;
}
