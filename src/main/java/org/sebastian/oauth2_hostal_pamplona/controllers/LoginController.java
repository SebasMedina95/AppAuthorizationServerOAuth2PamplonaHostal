package org.sebastian.oauth2_hostal_pamplona.controllers;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login"; // Inyecta un HTML automáticamente
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout"; // Inyecta un HTML automáticamente
    }

    // Aceptamos el consentimiento de darle clic a logout.
    @PostMapping("logout")
    public String logoutOk(HttpSecurity http) throws Exception {

        http.logout(logoutConfig ->
                logoutConfig.logoutSuccessUrl("login?logout")
                    .deleteCookies("JSESSIONID") //Eliminamos esta cookie porque tiene el ID de sesión.
                    .clearAuthentication(true)
                    .invalidateHttpSession(true));

        return "login?logout";

    }

}
