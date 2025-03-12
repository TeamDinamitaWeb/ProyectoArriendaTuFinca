package co.edu.javeriana.tufinca.entities;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@SQLDelete(sql = "UPDATE Arrendatario SET status = 1 WHERE id=?")
public class Arrendatario extends Usuario{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String contraseña;
    private String correo;
    private String telefono;

    @OneToOne
    @JoinColumn(name = "perfil_id")
    private Perfil id_perfil;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente id_cliente;
    @OneToOne
    @JoinColumn(name = "propiedad_id")
    private Propiedad id_propiedad;
}



