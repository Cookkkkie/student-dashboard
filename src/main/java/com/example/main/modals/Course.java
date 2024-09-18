package com.example.main.modals;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseID")
    private Long courseID;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * @Note
     * No idea why, but UserMod and Assignments broke the repository. I'm kinda impressed xddd
     * TODO
     * fix this
     */

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private UserMod user;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Assignment> assignments;

}
