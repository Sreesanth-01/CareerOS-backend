package com.project.careerOs.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    public String userName;
    public String mobile;
    public String email;
    public String password;
}
