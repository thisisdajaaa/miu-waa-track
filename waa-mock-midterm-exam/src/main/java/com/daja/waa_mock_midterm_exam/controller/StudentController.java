package com.daja.waa_mock_midterm_exam.controller;

import com.daja.waa_mock_midterm_exam.entity.dto.request.StudentDto;
import com.daja.waa_mock_midterm_exam.entity.dto.response.CourseDetailDto;
import com.daja.waa_mock_midterm_exam.entity.dto.response.StudentDetailDto;
import com.daja.waa_mock_midterm_exam.helper.QueryParamHelper;
import com.daja.waa_mock_midterm_exam.helper.ResponseHelper;
import com.daja.waa_mock_midterm_exam.service.spec.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final IStudentService studentService;

    @Autowired
    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<List<StudentDetailDto>>> getStudents(
            @RequestParam(required = false) String filter
    ) {
        Map<String, String> transformedFilter = QueryParamHelper.transformedFilter(filter);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved students!", studentService.findAll(transformedFilter)),
                HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<ResponseHelper.CustomResponse<StudentDetailDto>> getStudent(
            @PathVariable Long studentId
    ) {
        StudentDetailDto student = studentService.findById(studentId);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved student!", student),
                HttpStatus.OK);
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<ResponseHelper.CustomResponse<List<CourseDetailDto>>> getCoursesByStudentId(
            @PathVariable Long studentId
    ) {
        List<CourseDetailDto> courses = studentService.findCoursesByStudentId(studentId);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved courses for student!", courses),
                HttpStatus.OK);
    }

    @GetMapping("/gpa/{gpa}")
    public ResponseEntity<ResponseHelper.CustomResponse<List<StudentDetailDto>>> getStudentsByGpaLessOrEqual(
            @PathVariable double gpa
    ) {
        List<StudentDetailDto> students = studentService.findStudentsByGpaLessOrEqual(gpa);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved students with GPA less or equal to " + gpa + "!", students),
                HttpStatus.OK);
    }

    @GetMapping("/msc-program/gpa/{gpa}")
    public ResponseEntity<ResponseHelper.CustomResponse<List<StudentDetailDto>>> getStudentsInMscProgramWithGpaLessThan(
            @PathVariable double gpa
    ) {
        List<StudentDetailDto> students = studentService.findStudentsInMscProgramWithGpaLessThan(gpa);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved students in MSC program with GPA less than " + gpa + "!", students),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<StudentDetailDto>> createStudent(
            @RequestBody StudentDto studentDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created a student!", studentService.add(studentDto)),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<ResponseHelper.CustomResponse<StudentDetailDto>> deleteStudent(
            @PathVariable Long studentId
    ) {
        StudentDetailDto student = studentService.delete(studentId);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully deleted a student!", student),
                HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<ResponseHelper.CustomResponse<StudentDetailDto>> updateStudent(
            @PathVariable Long studentId,
            @RequestBody StudentDto studentDto
    ) {
        StudentDetailDto updatedStudent = studentService.update(studentId, studentDto);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully updated a student!", updatedStudent),
                HttpStatus.OK);
    }
}
