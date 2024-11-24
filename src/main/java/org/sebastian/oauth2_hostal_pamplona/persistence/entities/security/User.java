package org.sebastian.oauth2_hostal_pamplona.persistence.entities.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "TBL_USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Entidad que representa a los usuarios en BD")
public class User implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Comment("Clave primaria")
    @Schema(description = "Clave primaria autogenerada")
    private Long id;

    @Column(name = "FULL_NAME", nullable = false, length = 200 )
    @Comment("Nombre completo del usuario")
    private String fullName;

    @Column(name = "EMAIL", nullable = false, length = 200, unique = true )
    @Comment("Email del usuario")
    private String email;

    @Column(name = "USERNAME", nullable = false, length = 100, unique = true )
    @Comment("Username del usuario")
    private String username;

    @Column(name = "PASSWORD", nullable = false, length = 100 )
    @Comment("Password del usuario")
    private String password;

    @Column(name = "PHOTO", nullable = true, length = 300 )
    @Comment("URL foto del usuario")
    private String photo;

    @Column(name = "UPDATE_DATE_ROLE", nullable = true )
    @Comment("Fecha de actualización del rol")
    private OffsetDateTime updateDateRole;

    @Column(name = "UPDATE_USER_ROLE", nullable = true, length = 100 )
    @Comment("Usuario de actualización del rol")
    private String updateUserRole;

    @Column(name = "STATUS" )
    @Comment("Estado del usuario")
    private Boolean status;

    @Column(name = "VERIFY" )
    @Comment("Verificación del Usuario vía Email")
    private Boolean verify;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // *********************************************************** //
    // ***** METODOS IMPLEMENTADOS PARA TRABAJAR UserDetails ***** //
    // *********************************************************** //
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if( role == null ) return List.of();
        if( role.getPermissions() == null ) return List.of();

        // Para la extracción del authoritie lo obtenemos de la operación, pero, debemos sacarlo,
        // por tanto, lo extraemos del getName de la operación, así tendremos el authority.
        List<SimpleGrantedAuthority> authorities = role.getPermissions().stream()
                .map(each -> each.getOperation().getName())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Para que siga la convención para la validación anexamos "ROLE_"
        // La idea es que tenemos el nombre del rol y le concatenamos la convención de la palabra.
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role.getName()));
        return authorities;

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
