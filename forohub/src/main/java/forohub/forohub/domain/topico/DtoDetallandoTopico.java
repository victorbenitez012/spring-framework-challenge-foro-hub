package forohub.forohub.domain.topico;

import java.time.LocalDateTime;

public record DtoDetallandoTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        String curso,
        LocalDateTime fechaCreacion,
        String status) {
}
