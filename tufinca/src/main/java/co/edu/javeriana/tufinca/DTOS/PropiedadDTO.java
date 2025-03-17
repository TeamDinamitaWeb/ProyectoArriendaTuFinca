package co.edu.javeriana.tufinca.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropiedadDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String municipio;
    private int capacidad;
    private double precioPorNoche;
}
