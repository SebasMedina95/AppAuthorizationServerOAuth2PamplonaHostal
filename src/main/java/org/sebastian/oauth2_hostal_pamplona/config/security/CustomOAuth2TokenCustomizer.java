package org.sebastian.oauth2_hostal_pamplona.config.security;

import lombok.RequiredArgsConstructor;
import org.sebastian.oauth2_hostal_pamplona.common.exceptions.NotFound;
import org.sebastian.oauth2_hostal_pamplona.persistence.entities.security.User;
import org.sebastian.oauth2_hostal_pamplona.persistence.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomOAuth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final UserRepository userRepository;

    @Override
    public void customize(JwtEncodingContext context) {
        Authentication authentication = context.getPrincipal();
        String tokenType = context.getTokenType().getValue();

        //El que intercambio
        //Si es así agregarémos unos claims adicionales
        if( tokenType.equals("access_token") ){

            List<String> authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            context.getClaims().claim("permissions", authorities); // Este permissions lo requeriremos para la API Principal
        }

        if( tokenType.equals("id_token") ){

            String username = authentication.getName();

            User user = userRepository.getUserByUsername(username)
                    .orElseThrow(() -> new NotFound("Usuario no encontrado: " + username));

            context.getClaims().claim("email", user.getEmail());
            context.getClaims().claim("fullName", user.getFullName());

        }

    }

}
