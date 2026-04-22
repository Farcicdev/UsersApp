package farcic.dev.users.controller;

import farcic.dev.users.dto.request.UsersRequestDto;
import farcic.dev.users.dto.response.UsersResponseDto;
import farcic.dev.users.service.UsersService;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsersResponseDto createUsers(@Valid @RequestBody UsersRequestDto resquestDto) {
        return service.createUser(resquestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsersResponseDto> listarTodos(){
        return service.listar();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsersResponseDto buscarPorId(@PathVariable Long id){
        return service.findByIdUser(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UsersResponseDto alterar(@Valid @RequestBody UsersRequestDto resquestDto, @PathVariable Long id){
        return service.update(resquestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

}
