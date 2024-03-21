package com.rishita.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String jwt;
    private boolean status;

}
