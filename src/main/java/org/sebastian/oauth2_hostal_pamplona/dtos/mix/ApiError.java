package org.sebastian.oauth2_hostal_pamplona.dtos.mix;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError implements Serializable {

    private String backendMessage;
    private String message;
    private String url;
    private String method;
    private LocalDateTime timestamp;

}
