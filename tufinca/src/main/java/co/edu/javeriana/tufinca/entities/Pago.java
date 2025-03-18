package co.edu.javeriana.tufinca.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.persistence.*;
import lombok.*;

@SuppressWarnings("deprecation")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE Pago SET status = 1 WHERE id=?")
public class Pago implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private SolicitudArriendo solicitud;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private String banco;

    @Column(nullable = false)
    private String numeroCuenta;

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado;

    private int status = 0;

    public enum EstadoPago {
        PENDIENTE, COMPLETADO, RECHAZADO
    }
}
