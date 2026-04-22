package farcic.dev.users.dto.response;

public record UsersResponseDto(
        Long id,
        String name,
        String email,
        EnderecoResponseDto endereco
) {
}
