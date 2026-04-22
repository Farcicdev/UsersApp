package farcic.dev.users.service;

import farcic.dev.users.client.ViaCepClient;
import farcic.dev.users.dto.request.UsersRequestDto;
import farcic.dev.users.dto.response.UsersResponseDto;
import farcic.dev.users.dto.response.ViaCepResponseDto;
import farcic.dev.users.entity.UsersEntity;
import farcic.dev.users.exeption.UserAlreadyExistsException;
import farcic.dev.users.exeption.UserNotFoundException;
import farcic.dev.users.mapper.EnderecoMapper;
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
    private final ViaCepClient viaCepClient;
    private final EnderecoMapper mapperEndereco;

    //criar usuario
    public UsersResponseDto createUser(UsersRequestDto resquestDto) {
        if (repository.existsByEmail(resquestDto.email())) {
            throw new UserAlreadyExistsException("usuario ja existe");
        }

        ViaCepResponseDto cepResponse = viaCepClient.buscarCep(resquestDto.endereco().cep());

        if (cepResponse == null || Boolean.TRUE.equals(cepResponse.erro())) {
            throw new RuntimeException("CEP nao encontrado");
        }

        UsersEntity user = mapper.toModel(resquestDto);

        mapperEndereco.updateFromViaCep(user.getEndereco(), cepResponse);

        UsersEntity save = repository.save(user);
        return mapper.toResponse(save);
    }

    public List<UsersResponseDto> listar() {
        return repository.findAll().stream()
                .map(e -> mapper.toResponse(e))
                .toList();
    }


    //procurar por id
    public UsersResponseDto findByIdUser(Long id) {
        UsersResponseDto response = mapper.toResponse(repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Usuario nao existe")
        ));
        return response;
    }

    //atualizar cadastro
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

    //deletar usuario
    public void delete(Long id){
        UsersEntity usuario = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Usuario nao existe")
        );
        repository.deleteById(usuario.getId());
    }

}
