package com.daja.waa_mock_midterm_exam.controller;

import com.daja.waa_mock_midterm_exam.entity.dto.request.CourseDto;
import com.daja.waa_mock_midterm_exam.entity.dto.response.CourseDetailDto;
import com.daja.waa_mock_midterm_exam.helper.QueryParamHelper;
import com.daja.waa_mock_midterm_exam.helper.ResponseHelper;
import com.daja.waa_mock_midterm_exam.service.spec.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final ICourseService courseService;

    @Autowired
    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<List<CourseDetailDto>>> getCourses(
            @RequestParam(required = false) String filter
    ) {
        Map<String, String> transformedFilter = QueryParamHelper.transformedFilter(filter);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved courses!", courseService.findAll(transformedFilter)),
                HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<ResponseHelper.CustomResponse<CourseDetailDto>> getCourse(
            @PathVariable Long courseId
    ) {
        CourseDetailDto course = courseService.findById(courseId);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved course!", course),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<CourseDetailDto>> createCourse(
            @RequestBody CourseDto courseDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created a course!", courseService.add(courseDto)),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<ResponseHelper.CustomResponse<CourseDetailDto>> deleteCourse(
            @PathVariable Long courseId
    ) {
        CourseDetailDto course = courseService.delete(courseId);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully deleted a course!", course),
                HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{courseId}")
    public ResponseEntity<ResponseHelper.CustomResponse<CourseDetailDto>> updateCourse(
            @PathVariable Long courseId,
            @RequestBody CourseDto courseDto
    ) {
        CourseDetailDto updatedCourse = courseService.update(courseId, courseDto);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully updated a course!", updatedCourse),
                HttpStatus.OK);
    }
}
