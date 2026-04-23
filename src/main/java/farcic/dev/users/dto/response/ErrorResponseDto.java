package farcic.dev.users.dto.response;

import java.time.LocalDateTime;

public record ErrorResponseDto(

        String message,
        int status,
        LocalDateTime timestamp

) {
}
