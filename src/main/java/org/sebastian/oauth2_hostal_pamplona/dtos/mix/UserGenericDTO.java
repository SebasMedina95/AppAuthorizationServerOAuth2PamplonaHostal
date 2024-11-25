package org.sebastian.oauth2_hostal_pamplona.dtos.mix;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGenericDTO {

    private Long id;
    private String username;
    private String fullName;
    private String email;

}
