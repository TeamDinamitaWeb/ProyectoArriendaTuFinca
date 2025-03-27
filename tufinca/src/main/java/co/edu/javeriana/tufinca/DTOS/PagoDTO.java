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
public class PagoDTO {
    private Long id;
    private Long solicitudId;
    private double valor;
    private String banco;
    private String numeroCuenta;
    private LocalDateTime fechaPago;
    private String estado;
    private Integer status;
}
