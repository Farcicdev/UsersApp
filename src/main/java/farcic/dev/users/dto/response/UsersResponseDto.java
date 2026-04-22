package farcic.dev.users.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados retornados de usuario")
public record UsersResponseDto(
        @Schema(example = "1")
        Long id,

        @Schema(example = "Joao Silva")
        String name,

        @Schema(example = "joao.silva@example.com")
        String email,

        EnderecoResponseDto endereco
) {
}
