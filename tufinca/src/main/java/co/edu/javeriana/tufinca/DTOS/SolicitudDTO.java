package co.edu.javeriana.tufinca.DTOS;

import java.time.LocalDateTime;

import co.edu.javeriana.tufinca.entities.SolicitudArriendo.EstadoSolicitud;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudDTO {
    private Long id;
    private Long propiedadId;
    private Long arrendatarioId;
    private String nombrePropiedad;
    private String nombreSolicitante;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private double valor;
    private EstadoSolicitud estado;
    private Integer cantidadPersonas;
    private Integer status;
}
