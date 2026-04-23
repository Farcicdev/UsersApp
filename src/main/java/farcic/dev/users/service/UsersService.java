package farcic.dev.users.service;

import farcic.dev.users.client.ViaCepClient;
import farcic.dev.users.dto.request.ChangePasswordRequestDto;
import farcic.dev.users.dto.request.UsersRequestDto;
import farcic.dev.users.dto.request.UsersUpdateRequestDto;
import farcic.dev.users.dto.response.UsersResponseDto;
import farcic.dev.users.dto.response.ViaCepResponseDto;
import farcic.dev.users.entity.UsersEntity;
import farcic.dev.users.entity.UsersRole;
import farcic.dev.users.exeption.*;
import farcic.dev.users.mapper.EnderecoMapper;
import farcic.dev.users.mapper.UsersMapper;
import farcic.dev.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository repository;
    private final UsersMapper mapper;
    private final ViaCepClient viaCepClient;
    private final EnderecoMapper mapperEndereco;
    private final PasswordEncoder passwordEncoder;

    //@Post criar usuario
    public UsersResponseDto createUser(UsersRequestDto resquestDto) {
        if (repository.existsByEmail(resquestDto.email())) {
            throw new UserAlreadyExistsException("usuario ja existe");
        }

        ViaCepResponseDto cepResponse = viaCepClient.buscarCep(resquestDto.endereco().cep());

        if (cepResponse == null || Boolean.TRUE.equals(cepResponse.erro())) {
            throw new CepNotFoundException("CEP nao encontrado");
        }

        UsersEntity user = mapper.toModel(resquestDto);

        user.setRole(UsersRole.USER);

        user.setPassword(passwordEncoder.encode(resquestDto.password()));

        mapperEndereco.updateFromViaCep(user.getEndereco(), cepResponse);

        UsersEntity save = repository.save(user);
        return mapper.toResponse(save);
    }
    //@GET Listar
    public List<UsersResponseDto> listar() {
        return repository.findAll().stream()
                .map(e -> mapper.toResponse(e))
                .toList();
    }


    //@GET procurar por id
    public UsersResponseDto findByIdUser(Long id) {
        UsersResponseDto response = mapper.toResponse(repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Usuario nao existe")
        ));
        return response;
    }

    //@PUT atualizar cadastro
    public UsersResponseDto update(UsersUpdateRequestDto resquestDto, Long id) {
        validateUserAccess(id);
        UsersEntity user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario noa existe"));
        //validar se email existe
        if (repository.existsByEmailAndIdNot(resquestDto.email(), id)) {
            throw new UserAlreadyExistsException("usuario ja existe");
        }

        ViaCepResponseDto cepResponse = viaCepClient.buscarCep(resquestDto.endereco().cep());
        //verificar se o cep existe
        if (cepResponse == null || Boolean.TRUE.equals(cepResponse.erro())) {
            throw new CepNotFoundException("CEP nao encontrado");
        }

        mapper.updateEntity(user, resquestDto);

        mapperEndereco.updateFromViaCep(user.getEndereco(), cepResponse);

        UsersEntity saved = repository.save(user);



        return mapper.toResponse(saved);
    }

    //@PATCH alterar a senha
    public void changePassword(Long id, ChangePasswordRequestDto changePassword){
        validateUserAccess(id);
        UsersEntity user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario noa existe"));

        if (!passwordEncoder.matches(changePassword.currentPassword(), user.getPassword())) {
            throw new InvalidPasswordExeception("Senha atual invalida");
        }

        if (!changePassword.newPassword().equals(changePassword.confirmNewPassword())) {
            throw new PasswordConfirmationException("Nova senha e confirmacao nao conferem");
        }

        user.setPassword(passwordEncoder.encode(changePassword.newPassword()));

        repository.save(user);
    }

    //@DELETE deletar usuario
    public void delete(Long id){
        validateUserAccess(id);
        UsersEntity usuario = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Usuario nao existe")
        );
        repository.deleteById(usuario.getId());
    }

    //@DELETE
    public void deleteAll(){
        repository.deleteAll();
    }

    //valdacao de usuario pode alterar so sua conta
    private void validateUserAccess(Long id) {
        UsersEntity loggedUser = (UsersEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        boolean isAdmin = loggedUser.getRole() == UsersRole.ADMIN;
        boolean isOwner = loggedUser.getId().equals(id);

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("Voce nao tem permissao para acessar este recurso");
        }
    }

}
