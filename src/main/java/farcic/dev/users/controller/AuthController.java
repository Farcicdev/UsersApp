package farcic.dev.users.controller;

import farcic.dev.users.dto.request.LoginRequestDto;
import farcic.dev.users.dto.response.LoginResponseDto;
import farcic.dev.users.entity.UsersEntity;
import farcic.dev.users.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Operation(summary = "Autenticar usuario", description = "Valida email e senha do usuario e retorna um token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos"),
            @ApiResponse(responseCode = "401", description = "Credenciais invalidas")
    })
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto requestDto) {
        // Monta o objeto que o Spring Security usa para validar email e senha.
        var usernamePassword = new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password());

        // Autentica usando o UserDetailsService e o PasswordEncoder configurados.
        var auth = authenticationManager.authenticate(usernamePassword);

        // Gera o JWT para o usuario autenticado.
        var token = tokenService.generateToken((UsersEntity) auth.getPrincipal());

        return new LoginResponseDto(token);
    }
}
