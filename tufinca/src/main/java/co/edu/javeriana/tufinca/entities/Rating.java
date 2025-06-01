package co.edu.javeriana.tufinca.entities;

import java.io.Serializable;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "ratings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE ratings SET status = 1 WHERE id=?")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", nullable = false)
    private SolicitudArriendo solicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrendador_id", nullable = false)
    private Usuario arrendador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrendatario_id", nullable = false)
    private Usuario arrendatario;

    @ManyToOne
    @JoinColumn(name = "usuario_id") 
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "propiedad_id")
    private Propiedad propiedad;


    @Column(nullable = false)
    private Integer calificacionFinca;

    @Column(length = 500)
    private String comentarioFinca;

    @Column(nullable = false)
    private Integer calificacionArrendatario;

    @Column(length = 500)
    private String comentarioArrendatario;

    @Column(nullable = false)
    private Integer status = 0;
}
