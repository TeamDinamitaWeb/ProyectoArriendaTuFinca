package co.edu.javeriana.tufinca.DTOS;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagoDTO {
    private Long id;
    private double valor;
    private String banco;
    private String numeroCuenta;
    private LocalDateTime fechaPago;
    private String estado;
}
