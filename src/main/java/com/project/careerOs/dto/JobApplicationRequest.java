package com.project.careerOs.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplicationRequest {
    public String companyName;
    public String position;
    public long salary;
    public String status;
    public LocalDate appliedDate;
    public String notes;
}
