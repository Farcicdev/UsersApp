package farcic.dev.users.mapper;

import farcic.dev.users.dto.request.UsersRequestDto;
import farcic.dev.users.dto.response.UsersResponseDto;
import farcic.dev.users.entity.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersMapper {

    private final EnderecoMapper enderecoMapper;

    public UsersEntity toModel(UsersRequestDto requestDto) {
        return UsersEntity.builder()
                .name(requestDto.name())
                .email(requestDto.email())
                .password(requestDto.password())
                .endereco(enderecoMapper.toModel(requestDto.endereco()))
                .build();
    }

    public UsersResponseDto toResponse(UsersEntity entity) {
        return new UsersResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRole(),
                enderecoMapper.toResponse(entity.getEndereco())
        );
    }

    public void updateEntity(UsersEntity entity, UsersRequestDto requestDto) {
        entity.setName(requestDto.name());
        entity.setEmail(requestDto.email());
        entity.setPassword(requestDto.password());

        enderecoMapper.updateEntity(entity.getEndereco(), requestDto.endereco());
    }

}
