package com.project.careerOs.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    public String userName;
    public String mobile;
    public String email;
    public String password;
}
