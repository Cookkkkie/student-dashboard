package com.example.main.modals;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignmentID")
    private Long assignmentID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "dueDate", nullable = false)
    private String dueDate;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "courseID", nullable = false)
    private Course course;

    public Assignment() {}

    public Assignment(String name, String dueDate, String status, Course course) {
        this.name = name;
        this.dueDate = dueDate;
        this.status = status;
        this.course = course;
    }

}
