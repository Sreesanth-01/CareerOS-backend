package com.project.careerOs.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    public String email;
    public String password;
}
