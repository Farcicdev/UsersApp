package farcic.dev.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Schema(description = "Dados para criacao ou atualizacao de usuario")
public record UsersRequestDto(
        @NotBlank
        @Size(max = 100)
        @Schema(example = "Joao Silva")
        String name,

        @NotBlank
        @Email(message = "Email invalido")
        @Schema(example = "joao.silva@example.com")
        String email,

        @NotBlank
        @Schema(example = "Senha123")
        @Size(min = 8, max = 100, message = "Senha deve ter entre 8 e 100 caracteres")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
                message = "Senha deve conter letras e numeros"
        )
        String password,

        @Valid
        @NotNull
        @Schema(description = "Endereco do usuario")
        EnderecoRequestDto endereco
) {
}
