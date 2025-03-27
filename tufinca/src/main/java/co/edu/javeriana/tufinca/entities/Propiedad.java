package co.edu.javeriana.tufinca.entities;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE Propiedad SET status = 1 WHERE id=?")
public class Propiedad {
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   private String nombre;
   private String descripcion;
   private String direccion;
   private String municipio;
   private int capacidad;
   private double precioPorNoche;
   private String estado;
   
   @Column(nullable = false)
    private Integer status = 0;
}
