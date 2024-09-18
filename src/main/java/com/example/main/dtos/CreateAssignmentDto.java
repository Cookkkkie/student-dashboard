package com.example.main.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class CreateAssignmentDto {
    public String assignmentName;
    public String assignmentStatus;
    public LocalDate assignmentDate;
    public Long courseID;

}


