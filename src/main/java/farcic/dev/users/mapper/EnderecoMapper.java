package farcic.dev.users.mapper;

import farcic.dev.users.dto.request.EnderecoRequestDto;
import farcic.dev.users.dto.response.EnderecoResponseDto;
import farcic.dev.users.dto.response.ViaCepResponseDto;
import farcic.dev.users.entity.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    public EnderecoEntity toModel(EnderecoRequestDto requestDto) {
        return EnderecoEntity.builder()
                .numero(requestDto.numero())
                .complemento(requestDto.complemento())
                .cep(requestDto.cep())
                .build();
    }

    public EnderecoResponseDto toResponse(EnderecoEntity entity) {
        return new EnderecoResponseDto(
                entity.getId(),
                entity.getRua(),
                entity.getNumero(),
                entity.getComplemento(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getEstado(),
                entity.getCep()
        );
    }

    public void updateEntity(EnderecoEntity entity, EnderecoRequestDto requestDto) {
        entity.setNumero(requestDto.numero());
        entity.setComplemento(requestDto.complemento());
        entity.setCep(requestDto.cep());
    }

    public void updateFromViaCep(EnderecoEntity endereco, ViaCepResponseDto cepResponse) {
        endereco.setRua(cepResponse.logradouro());
        endereco.setBairro(cepResponse.bairro());
        endereco.setCidade(cepResponse.localidade());
        endereco.setEstado(cepResponse.uf());
        endereco.setCep(cepResponse.cep());
    }

}
