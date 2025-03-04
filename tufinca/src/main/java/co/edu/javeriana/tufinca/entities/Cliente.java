package co.edu.javeriana.tufinca.entities;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE Cliente SET status = 1 WHERE id=?")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;
    private String nombre;

    @OneToMany
    @JoinColumn(name = "id_cliente")
    private List<Orden> ordenes;

    @OneToMany
    @JoinColumn(name = "id_cliente")
    private List<Usuario> usuarios;
}
