package com.daja.waa_mock_midterm_exam.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDetailDto {
    private Long id;
    private String name;
    private double gpa;
    private AddressDetailDto address;
    private Set<CourseDetailDto> courses;
}
