package farcic.dev.users.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Token JWT retornado apos autenticacao")
public record LoginResponseDto(
        @Schema(example = "eyJhbGciOiJIUzI1NiJ9...")
        String token
) {
}
