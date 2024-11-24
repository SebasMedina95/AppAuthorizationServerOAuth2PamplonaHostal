package org.sebastian.oauth2_hostal_pamplona.persistence.entities.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// =========================================================================================
// En pocas palabras, esta entidad representa las aplicaciones que se van a conectar
// a nuestro servicio. Para poder solicitar códigos de autorización y tokens de acceso.
// =========================================================================================

@Entity
@Table(name = "TBL_CLIENT_APP_OAUTH2")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Entidad que representa al Client App")
public class ClientApp {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String clientId;

    private String clientSecret;

    @ElementCollection(fetch = FetchType.EAGER) // Crea una tabla llamada clientAuthenticationMethods y realiza una relación oneToMany
    private List<String> clientAuthenticationMethods;

    @ElementCollection(fetch = FetchType.EAGER) // Crea una tabla llamada authorizationGrantTypes y realiza una relación oneToMany
    private List<String> authorizationGrantTypes;

    @ElementCollection(fetch = FetchType.EAGER) // Crea una tabla llamada redirectUris y realiza una relación oneToMany
    private List<String> redirectUris;

    @ElementCollection(fetch = FetchType.EAGER) // Crea una tabla llamada scopes y realiza una relación oneToMany
    private List<String> scopes;

    private int durationInMinutes;

    private boolean requiredProofKey;

}
