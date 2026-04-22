package farcic.dev.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para criacao ou atualizacao de usuario")
public record UsersRequestDto(
        @NotBlank
        @Size(max = 100)
        @Schema(example = "Joao Silva")
        String name,

        @NotBlank
        @Email
        @Schema(example = "joao.silva@example.com")
        String email,

        @NotBlank
        @Schema(example = "123456")
        String password,

        @Valid
        @NotNull
        @Schema(description = "Endereco do usuario")
        EnderecoRequestDto endereco
) {
}
