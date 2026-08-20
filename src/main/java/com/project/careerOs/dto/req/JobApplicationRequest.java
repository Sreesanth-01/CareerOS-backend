package com.project.careerOs.dto.req;

import java.time.LocalDate;

import com.project.careerOs.model.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplicationRequest {
    public String companyName;
    public String jobRole;
    public Long salary;
    public Status status;
    public LocalDate appliedDate;
    public String notes;
}
