package com.project.careerOs.dto.req;

import java.time.LocalDate;

import com.project.careerOs.model.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateExpenseRequest {
    public String companyName;
    public String jobRole;
    public long salary;
    public Status status;
    public LocalDate appliedDate;
    public String notes;

}
