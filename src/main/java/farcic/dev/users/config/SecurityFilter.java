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

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsersRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Tenta recuperar o token enviado no header Authorization.
        String token = recoverToken(request);

        if (token != null) {
            // Valida o token e extrai o email salvo no subject.
            String email = tokenService.validateToken(token);

            repository.findByEmail(email).ifPresent(user -> {
                // Registra o usuario autenticado no contexto do Spring Security.
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        }

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
