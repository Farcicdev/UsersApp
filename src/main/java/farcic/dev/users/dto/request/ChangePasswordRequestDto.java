package farcic.dev.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para alteracao de senha do usuario")
public record ChangePasswordRequestDto(


        @NotBlank
        @Schema(example = "SenhaAtual123")
        String currentPassword,

        @NotBlank
        @Size(min = 8, max = 100, message = "Senha deve ter entre 8 e 100 caracteres")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
                message = "Senha deve conter letras e numeros"
        )
        @Schema(example = "NovaSenha123")
        String newPassword,


        @NotBlank
        @Schema(example = "NovaSenha123")
        String confirmNewPassword

) {
}
