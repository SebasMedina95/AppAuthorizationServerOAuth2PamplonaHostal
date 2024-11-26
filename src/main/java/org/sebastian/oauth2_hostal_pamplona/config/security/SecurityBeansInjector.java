package org.sebastian.oauth2_hostal_pamplona.config.security;

import org.sebastian.oauth2_hostal_pamplona.common.exceptions.NotFound;
import org.sebastian.oauth2_hostal_pamplona.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansInjector {

    private UserRepository userRepository;

    @Autowired
    public SecurityBeansInjector(
        UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
        authenticationStrategy.setPasswordEncoder( passwordEncoder() );
        authenticationStrategy.setUserDetailsService( userDetailsService() );
        return authenticationStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username ->
            userRepository
                .getUserByUsername(username)
                .orElseThrow(() -> new NotFound("Usuario no encontrado con username: " + username));

    }

}
