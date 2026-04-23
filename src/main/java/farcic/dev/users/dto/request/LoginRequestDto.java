package farcic.dev.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados de autenticacao do usuario")
public record LoginRequestDto(

        @NotBlank
        @Email
        @Schema(example = "joao.silva@example.com")
        String email,

        @NotBlank
        @Schema(example = "123456")
        String password

) {
}
