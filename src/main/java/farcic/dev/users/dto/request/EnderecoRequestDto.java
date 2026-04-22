package farcic.dev.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDto(
        @NotBlank
        @Size(max = 20)
        String numero,
        @Size(max = 100)
        String complemento,
        @NotBlank
        @Size(max = 9)
        String cep
) {
}
