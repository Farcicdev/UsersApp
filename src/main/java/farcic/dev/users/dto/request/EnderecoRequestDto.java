package farcic.dev.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDto(
        @NotBlank
        @Size(max = 120)
        String rua,
        @NotBlank
        @Size(max = 20)
        String numero,
        @Size(max = 100)
        String complemento,
        @NotBlank
        @Size(max = 100)
        String bairro,
        @NotBlank
        @Size(max = 100)
        String cidade,
        @NotBlank
        @Pattern(regexp = "[A-Z]{2}")
        String estado,
        @NotBlank
        @Size(max = 9)
        String cep
) {
}
