package farcic.dev.users.client;

import farcic.dev.users.dto.response.ViaCepResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ViaCepClient {


    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://viacep.com.br/ws")
            .build();

    public ViaCepResponseDto buscarCep(String cep) {
        return restClient.get()
                .uri("/{cep}/json/", cep)
                .retrieve()
                .body(ViaCepResponseDto.class);
    }
}
