package com.daja.waa_mock_midterm_exam.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {
    private String name;
    private double gpa;
    private Long addressId;
    private Set<Long> courseIds;
}
