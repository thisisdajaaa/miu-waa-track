package com.daja.waa_mock_midterm_exam.service.spec;



import com.daja.waa_mock_midterm_exam.entity.dto.request.StudentDto;
import com.daja.waa_mock_midterm_exam.entity.dto.response.CourseDetailDto;
import com.daja.waa_mock_midterm_exam.entity.dto.response.StudentDetailDto;

import java.util.List;
import java.util.Map;

public interface IStudentService {
    List<StudentDetailDto> findAll(Map<String, String> filters);

    StudentDetailDto findById(Long id);

    StudentDetailDto add(StudentDto studentDto);

    StudentDetailDto delete(Long id);

    StudentDetailDto update(Long id, StudentDto updatedDto);

    List<CourseDetailDto> findCoursesByStudentId(Long studentId);

    List<StudentDetailDto> findStudentsByGpaLessOrEqual(double gpa);

    List<StudentDetailDto> findStudentsInMscProgramWithGpaLessThan(double gpa);
}
