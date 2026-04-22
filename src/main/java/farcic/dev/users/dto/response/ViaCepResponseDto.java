package farcic.dev.users.dto.response;


public record ViaCepResponseDto (
    String cep,
    String logradouro,
    String complemento,
    String bairro,
    String localidade,
    String uf,
    Boolean erro
){}
