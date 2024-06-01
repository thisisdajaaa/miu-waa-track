package com.daja.waa_mock_midterm_exam.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false)
    private double gpa;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "StudentCourse",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();
}
