package farcic.dev.users.config;

import farcic.dev.users.repository.UsersRepository;
import farcic.dev.users.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsersRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Tenta recuperar o token JWT do header Authorization.
        String token = recoverToken(request);

        if (token != null) {
            // Extrai o email do usuario salvo no token.
            String email = tokenService.validateToken(token);

            // Recupera a data em que o token foi emitido.
            Date issuedAt = tokenService.getIssuedAt(token);

            repository.findByEmail(email).ifPresent(user -> {
                // Se o usuario trocou a senha depois que o token foi emitido,
                // esse token antigo deixa de ser valido.
                if (user.getPasswordChangedAt() != null &&
                        issuedAt.toInstant().isBefore(
                                user.getPasswordChangedAt().atZone(ZoneId.systemDefault()).toInstant()
                        )) {
                    return;
                }

                // Se o token ainda e valido, autentica o usuario no contexto do Spring Security.
                var authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        }

        // Continua a cadeia de filtros da requisicao.
        filterChain.doFilter(request, response);
    }


    private String recoverToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        // O padrao esperado e: Authorization: Bearer <token>.
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }

        // Remove o prefixo "Bearer " e retorna somente o token.
        return authorizationHeader.substring(7);
    }
}
