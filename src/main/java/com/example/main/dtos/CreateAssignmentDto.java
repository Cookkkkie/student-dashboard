package com.example.main.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAssignmentDto {
    public String assignmentName;
    public String assignmentStatus;
    public String assignmentDate;
    public Long courseID;

}


