package org.sebastian.oauth2_hostal_pamplona.config.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@EnableWebSecurity
@Component
public class AuthorizationSecurityConfig {

    @Bean
    @Order(1) //Primen en la cadena de Filtros en el FilterProxy
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

        //Aquí se hará toda la inyección de métodos, filtros, configuraciones requeridas y necesarias.
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        //Habilitamos el OpenID Connect 1.0 y la configuración por defecto de JWT.
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
        http.exceptionHandling(exceptionConfig -> exceptionConfig.authenticationEntryPoint( new LoginUrlAuthenticationEntryPoint("/login")));
        http.oauth2ResourceServer(oauthResourceServerConfig -> oauthResourceServerConfig.jwt(Customizer.withDefaults())); //Buscará por defecto un Bean JWKSource

        return http.build();

    }

    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authConfig -> {
            authConfig.requestMatchers("/login").permitAll();
            authConfig.anyRequest().authenticated();
        });
        //Tomado desde la carpeta templates en mis resources.
        http.formLogin(formLoginConfig -> formLoginConfig.loginPage("/login"));

        return http.build();

    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    // Para la decodificación del Token
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    // Para indicar como lo vamos a decodificar
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        // Tener en cuenta si estamos con el perfil Docker o no
        // Mi API de Hostal Pamplona le preguntará a http://localhost:9776/authorization-server como decodificar el access token.
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:9776/authorization-server") // ¿Quién generó los Tokens?
                .build();
    }

    // Usamos el que nos trae la documentación por defecto.
    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

}
