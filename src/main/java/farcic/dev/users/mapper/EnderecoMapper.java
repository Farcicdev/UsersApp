package farcic.dev.users.mapper;

import farcic.dev.users.dto.request.EnderecoRequestDto;
import farcic.dev.users.dto.response.EnderecoResponseDto;
import farcic.dev.users.entity.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    public EnderecoEntity toModel(EnderecoRequestDto requestDto) {
        return EnderecoEntity.builder()
                .rua(requestDto.rua())
                .numero(requestDto.numero())
                .complemento(requestDto.complemento())
                .bairro(requestDto.bairro())
                .cidade(requestDto.cidade())
                .estado(requestDto.estado())
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
        entity.setRua(requestDto.rua());
        entity.setNumero(requestDto.numero());
        entity.setComplemento(requestDto.complemento());
        entity.setBairro(requestDto.bairro());
        entity.setCidade(requestDto.cidade());
        entity.setEstado(requestDto.estado());
        entity.setCep(requestDto.cep());
    }

}
