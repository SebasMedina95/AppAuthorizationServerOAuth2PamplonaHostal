package org.sebastian.oauth2_hostal_pamplona.persistence.entities.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "TBL_OPERATIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Entidad que representa a las operaciones de los roles en BD")
public class Operation {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Comment("Clave primaria")
    @Schema(description = "Clave primaria autogenerada")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 250 )
    @Comment("Nombre completo de la operación del rol")
    private String name;

    @Column(name = "PATH", nullable = false, length = 200 )
    @Comment("Path base de la operación, /products/{id}, /rooms, ...")
    private String path;

    @Column(name = "httpMethod", nullable = false, length = 200 )
    @Comment("Verbo de la operación, GET, POST, ...")
    private String httpMethod;

    @Column(name = "PERMIT_ALL", nullable = false )
    @Comment("Permisión total de la operación")
    private boolean permitAll;

    @Column(name = "MODULE_ID")
    private long moduleId;

}
