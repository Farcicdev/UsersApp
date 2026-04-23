package farcic.dev.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de endereco informados pelo usuario")
public record EnderecoRequestDto(
        @NotBlank
        @Size(max = 20)
        @Schema(example = "100")
        String numero,

        @Size(max = 100)
        @Schema(example = "Apto 12")
        String complemento,

        @NotBlank
        @Size(max = 9)
        @Schema(example = "01001-000")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP deve ter 8 digitos")
        String cep
) {
}
