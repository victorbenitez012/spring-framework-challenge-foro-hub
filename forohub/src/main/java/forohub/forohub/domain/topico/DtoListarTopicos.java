package forohub.forohub.domain.topico;

import java.time.LocalDateTime;

public record DtoListarTopicos(
        Long id,
        String titulo,
        String autor,
        String curso,
        String mensaje,
        String status,
        LocalDateTime fechaCreacion) {

    public DtoListarTopicos(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getAutor(),
                topico.getCurso(),
                topico.getMensaje(),
                topico.getStatus().toString(),
                topico.getFechaCreacion());
    }
}




