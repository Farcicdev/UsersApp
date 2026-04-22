package farcic.dev.users.dto.response;

public record EnderecoResponseDto(
        Long id,
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep
) {
}
