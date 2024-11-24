package org.sebastian.oauth2_hostal_pamplona.persistence.entities.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.List;

@Entity
@Table(name = "TBL_ROLES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Entidad que representa a los roles en BD")
public class Role {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Comment("Clave primaria")
    @Schema(description = "Clave primaria autogenerada")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 200 )
    @Comment("Nombre completo del rol")
    private String name;

    //? Lo usaremos para romper la relación muchos a muchos entre el rol y los permisos
    //? Colocamos el FetchType.EAGER para que nos cargue de una vez toda la data.
    @JsonIgnore //Para romper recursividad infinita
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<GrantedPermission> permissions;

}
