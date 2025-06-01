package co.edu.javeriana.tufinca.DTOS;

import co.edu.javeriana.tufinca.entities.Propiedad.EstadoPropiedad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropiedadDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String direccion;
    private String municipio;
    private int capacidad;
    private double precioPorNoche;
    private EstadoPropiedad estado;
    private Integer status;
}
