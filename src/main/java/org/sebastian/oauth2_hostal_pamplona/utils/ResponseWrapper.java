package org.sebastian.oauth2_hostal_pamplona.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {

    private T data;
    private String errorMessage;

}
