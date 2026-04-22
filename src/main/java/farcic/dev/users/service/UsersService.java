package farcic.dev.users.service;

import farcic.dev.users.dto.request.UsersRequestDto;
import farcic.dev.users.dto.response.UsersResponseDto;
import farcic.dev.users.entity.UsersEntity;
import farcic.dev.users.exeption.UserAlreadyExistsException;
import farcic.dev.users.exeption.UserNotFoundException;
import farcic.dev.users.mapper.UsersMapper;
import farcic.dev.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository repository;
    private final UsersMapper mapper;

    public UsersResponseDto createUser(UsersRequestDto resquestDto) {
        if (repository.existsByEmail(resquestDto.email())) {
            throw new UserAlreadyExistsException("usuario ja existe");
        }

        UsersEntity save = repository.save(mapper.toModel(resquestDto));
        return mapper.toResponse(save);
    }

    public List<UsersResponseDto> listar() {
        return repository.findAll().stream()
                .map(e -> mapper.toResponse(e))
                .toList();
    }

    public UsersResponseDto findByIdUser(Long id) {
        UsersResponseDto response = mapper.toResponse(repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Usuario nao existe")
        ));
        return response;
    }

    public UsersResponseDto update(UsersRequestDto resquestDto, Long id) {
        UsersEntity user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario noa existe"));

        if (repository.existsByEmail(resquestDto.email())) {
            throw new UserAlreadyExistsException("usuario ja existe");
        }

        mapper.updateEntity(user, resquestDto);

        UsersEntity saved = repository.save(user);

        return mapper.toResponse(saved);
    }

    public void delete(Long id){
        UsersEntity usuario = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Usuario nao existe")
        );
        repository.deleteById(usuario.getId());
    }

}
