package com.daja.waa_mock_midterm_exam.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetailDto {
    private Long id;
    private String name;
    private String courseDescription;
    private Integer credit;
    private String program;
    private Integer lastUpdated;
}
