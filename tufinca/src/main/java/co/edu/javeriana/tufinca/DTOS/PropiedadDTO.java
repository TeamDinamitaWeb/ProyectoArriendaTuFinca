package co.edu.javeriana.tufinca.DTOS;

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
    private String nombre;
    private String descripcion;
    private String direccion;
    private String municipio;
    private int capacidad;
    private double precioPorNoche;
    private String estado;
    private Integer status;
}
