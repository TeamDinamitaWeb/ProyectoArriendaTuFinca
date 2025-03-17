package co.edu.javeriana.tufinca.entities;

import java.io.Serializable;
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
@SQLDelete(sql = "UPDATE Rating SET status = 1 WHERE id=?")
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private SolicitudArriendo solicitud;

    @ManyToOne
    @JoinColumn(name = "arrendador_id", nullable = false)
    private Usuario arrendador;

    @ManyToOne
    @JoinColumn(name = "arrendatario_id", nullable = false)
    private Usuario arrendatario;

    @Column(nullable = false)
    private Integer calificacionFinca;

    @Column(length = 500)
    private String comentarioFinca;

    @Column(nullable = false)
    private Integer calificacionArrendatario;

    @Column(length = 500)
    private String comentarioArrendatario;

    private int status = 0;
}
