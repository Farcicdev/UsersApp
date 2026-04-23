package farcic.dev.users.controller;

import farcic.dev.users.dto.request.ChangePasswordRequestDto;
import farcic.dev.users.dto.request.UsersRequestDto;
import farcic.dev.users.dto.request.UsersUpdateRequestDto;
import farcic.dev.users.dto.response.UsersResponseDto;
import farcic.dev.users.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService service;

    @Operation(summary = "Criar usuario", description = "Cria um novo usuario com endereco preenchido a partir do CEP")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos ou CEP nao encontrado"),
            @ApiResponse(responseCode = "409", description = "Email ja cadastrado")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsersResponseDto createUsers(@Valid @RequestBody UsersRequestDto resquestDto) {
        return service.createUser(resquestDto);
    }

    @Operation(summary = "Listar usuarios", description = "Retorna todos os usuarios cadastrados")
    @ApiResponse(responseCode = "200", description = "Usuarios retornados com sucesso")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsersResponseDto> listarTodos() {
        return service.listar();
    }

    @Operation(summary = "Buscar usuario por ID", description = "Retorna um usuario pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsersResponseDto buscarPorId(@PathVariable Long id) {
        return service.findByIdUser(id);
    }

    @Operation(summary = "Atualizar usuario", description = "Atualiza os dados de um usuario existente")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Usuario atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos ou CEP nao encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado"),
            @ApiResponse(responseCode = "409", description = "Email ja cadastrado")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UsersResponseDto alterar(@Valid @RequestBody UsersUpdateRequestDto resquestDto, @PathVariable Long id) {
        return service.update(resquestDto, id);
    }

    @Operation(summary = "Alterar senha", description = "Altera a senha do usuario informado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Senha invalida ou confirmacao incorreta"),
            @ApiResponse(responseCode = "403", description = "Sem permissao para alterar a senha"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    })
    @PatchMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword( @PathVariable Long id,@Valid @RequestBody ChangePasswordRequestDto requestDto){
        service.changePassword(id, requestDto);
    }

    @Operation(summary = "Excluir usuario", description = "Remove um usuario pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario excluido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(summary = "Excluir todos os usuarios", description = "Remove todos os usuarios cadastrados. Somente ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuarios excluidos com sucesso"),
            @ApiResponse(responseCode = "403", description = "Sem permissao para excluir todos os usuarios")
    })
    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        service.deleteAll();
    }

}
