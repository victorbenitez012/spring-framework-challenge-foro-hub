package forohub.forohub.domain.topico;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Table(name= "topicos")
@Entity(name= "Topico")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
@Data


public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String autor;
    private String curso;

//Constructor con DtoRegistarNuevoTopico
    public Topico(DtoRegistrarNuevoTopico dtoRegistrarNuevoTopico) {
        this.titulo = dtoRegistrarNuevoTopico.titulo();
        this.mensaje = dtoRegistrarNuevoTopico.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.status = Status.ABIERTO;
        this.autor = dtoRegistrarNuevoTopico.autor();
        this.curso = dtoRegistrarNuevoTopico.curso();
    }
//Metodo para actualizar topicos
    public void actualizarTopicos(DtoActualizarTopico dtoActualizarTopico) {
        if (dtoActualizarTopico.titulo() != null) {
            this.titulo = dtoActualizarTopico.titulo();
        }
        if (dtoActualizarTopico.mensaje() != null) {
            this.mensaje = dtoActualizarTopico.mensaje();
        }
        if (dtoActualizarTopico.autor() != null) {
            this.autor = dtoActualizarTopico.autor();
        }
        if (dtoActualizarTopico.curso() != null) {
            this.curso = dtoActualizarTopico.curso();
        }
    }

}

