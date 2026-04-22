package farcic.dev.users.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados retornados de endereco")
public record EnderecoResponseDto(
        @Schema(example = "1")
        Long id,

        @Schema(example = "Praca da Se")
        String rua,

        @Schema(example = "100")
        String numero,

        @Schema(example = "Apto 12")
        String complemento,

        @Schema(example = "Se")
        String bairro,

        @Schema(example = "Sao Paulo")
        String cidade,

        @Schema(example = "SP")
        String estado,

        @Schema(example = "01001-000")
        String cep
) {
}
