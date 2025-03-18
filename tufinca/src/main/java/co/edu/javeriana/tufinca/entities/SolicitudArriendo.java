package co.edu.javeriana.tufinca.entities;

import java.io.Serializable;
import java.time.LocalDate;
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
@SQLDelete(sql = "UPDATE SolicitudArriendo SET status = 1 WHERE id=?")
public class SolicitudArriendo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "arrendatario_id", nullable = false)
    private Usuario arrendatario;

    @ManyToOne
    @JoinColumn(name = "propiedad_id", nullable = false)
    private Propiedad propiedad;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private Integer cantidadPersonas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;

    @Column(nullable = false)
    private LocalDateTime fechaSolicitud;

    private int status = 0; // Para eliminación lógica
    // Inicializacion en service

    public enum EstadoSolicitud {
        PENDIENTE, APROBADA, RECHAZADA, PAGADA, CALIFICADA, CERRADA
    }
}
