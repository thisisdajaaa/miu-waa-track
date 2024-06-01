package com.daja.waa_mock_midterm_exam.service.spec;

import com.daja.waa_mock_midterm_exam.entity.dto.request.CourseDto;
import com.daja.waa_mock_midterm_exam.entity.dto.response.CourseDetailDto;

import java.util.List;
import java.util.Map;

public interface ICourseService {
    List<CourseDetailDto> findAll(Map<String, String> filters);

    CourseDetailDto findById(Long id);

    CourseDetailDto add(CourseDto courseDto);

    CourseDetailDto delete(Long id);

    CourseDetailDto update(Long id, CourseDto updatedDto);
}
