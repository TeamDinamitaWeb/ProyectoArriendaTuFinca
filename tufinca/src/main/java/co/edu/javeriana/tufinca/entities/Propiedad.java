package co.edu.javeriana.tufinca.entities;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "propiedades")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE propiedades SET status = 1 WHERE id=?")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Propiedad {
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   @Column(nullable = false)
   private String titulo;

   private String descripcion;

   private String direccion;

   private String municipio;

   @Column(nullable = false)
   private int capacidad;

   @Column(nullable = false)
   private double precioPorNoche;

   @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPropiedad estado;

   // Relación con Usuario (Arrendador)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relación con Rating
    @OneToMany(mappedBy = "propiedad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    // Relación con Solicitudes
    @OneToMany(mappedBy = "propiedad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudArriendo> solicitudes;

    // Relación con Pagos
    @OneToMany(mappedBy = "propiedad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos;
   
   @Column(nullable = false)
    private Integer status = 0;

    public enum EstadoPropiedad {
        DISPONIBLE,
        NO_DISPONIBLE
    }
}