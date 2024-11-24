package org.sebastian.oauth2_hostal_pamplona.persistence.entities.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Entity
@Table(name = "TBL_JWTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Entidad que representa a los jwts ya generados")
public class JwtToken {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Comment("Clave primaria")
    @Schema(description = "Clave primaria autogenerada")
    private Long id;

    @Column(name = "TOKEN", nullable = false, length = 3500 )
    @Comment("Token de accesibilidad")
    private String token;

    @Column(name = "EXPIRATION", nullable = false)
    @Comment("Fecha de Vencimiento del Token")
    private Date expiration;

    @Column(name = "IS_VALID", nullable = false)
    @Comment("El token sigue siendo válido")
    private boolean isValid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
