package forohub.forohub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DtoAutenticacionUsuario(


        @NotBlank
        String login,

        @NotBlank
        String contrasena) {
}
