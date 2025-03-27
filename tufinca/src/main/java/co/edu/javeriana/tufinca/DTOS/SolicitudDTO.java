package co.edu.javeriana.tufinca.DTOS;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private LocalDateTime fechaLlegada;
    private LocalDateTime fechaSalida;
    private double valor;
    private String estado;
    private Integer cantidadPersonas;
    private Integer status;
}
