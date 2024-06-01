package com.daja.waa_mock_midterm_exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "course_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_description")
    private String courseDescription;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "program")
    private String program;

    @Column(name = "last_updated")
    private Integer lastUpdated;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    private Course course;

    @Override
    public int hashCode() {
        return Objects.hash(id, courseDescription, credit, program, lastUpdated);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CourseDetail courseDetail = (CourseDetail) obj;
        return Objects.equals(id, courseDetail.id) &&
                Objects.equals(courseDescription, courseDetail.courseDescription) &&
                Objects.equals(credit, courseDetail.credit) &&
                Objects.equals(program, courseDetail.program) &&
                Objects.equals(lastUpdated, courseDetail.lastUpdated);
    }
}
