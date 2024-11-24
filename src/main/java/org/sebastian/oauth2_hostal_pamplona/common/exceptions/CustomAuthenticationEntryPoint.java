package org.sebastian.oauth2_hostal_pamplona.common.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        // Registro en los logs
        log.error("Acceso no permitido al sistema: {}", authException.getMessage());

        // Establecer el estado y enviar el mensaje de error personalizado
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter()
                .write(
                        "{\"error\": \"ACCESO DENEGADO. No puede acceder al recurso solicitado. Por favor revise los " +
                                "permisos que tiene su rol en el sistema, si el token ha sido proporcionado y si el token " +
                                "sigue en vigencia. Si considera que se trata de un error, comuniquese con el ADMIN.\"}");
    }

}
