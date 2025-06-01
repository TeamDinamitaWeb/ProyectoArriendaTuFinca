package co.edu.javeriana.tufinca.DTOS;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import co.edu.javeriana.tufinca.entities.Pago.EstadoPago;
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
    private BigDecimal valor;
    private String banco;
    private String numeroCuenta;
    private LocalDateTime fechaPago;
    private EstadoPago estado;
    private Integer status;
}
