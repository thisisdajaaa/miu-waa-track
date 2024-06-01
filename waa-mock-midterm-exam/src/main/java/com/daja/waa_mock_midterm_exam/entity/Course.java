package com.daja.waa_mock_midterm_exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private CourseDetail courseDetails;

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return Objects.equals(id, course.id) &&
                Objects.equals(name, course.name);
    }
}
