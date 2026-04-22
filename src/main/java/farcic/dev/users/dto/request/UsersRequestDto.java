package farcic.dev.users.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsersRequestDto(
        @NotBlank
        @Size(max = 100)
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @Valid
        @NotNull
        EnderecoRequestDto endereco
) {
}
