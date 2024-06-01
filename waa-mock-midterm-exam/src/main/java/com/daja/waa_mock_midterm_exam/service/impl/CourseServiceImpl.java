package com.daja.waa_mock_midterm_exam.service.impl;

import com.daja.waa_mock_midterm_exam.configuration.MapperConfiguration;
import com.daja.waa_mock_midterm_exam.entity.Course;
import com.daja.waa_mock_midterm_exam.entity.CourseDetail;
import com.daja.waa_mock_midterm_exam.entity.dto.request.CourseDto;
import com.daja.waa_mock_midterm_exam.entity.dto.response.CourseDetailDto;
import com.daja.waa_mock_midterm_exam.exception.CourseException;
import com.daja.waa_mock_midterm_exam.repository.ICourseRepository;
import com.daja.waa_mock_midterm_exam.service.spec.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements ICourseService {
    private final ICourseRepository courseRepository;
    private final MapperConfiguration mapperConfiguration;

    @Autowired
    public CourseServiceImpl(ICourseRepository courseRepository, MapperConfiguration mapperConfiguration) {
        this.courseRepository = courseRepository;
        this.mapperConfiguration = mapperConfiguration;
    }

    @Override
    public List<CourseDetailDto> findAll(Map<String, String> filters) {
        return courseRepository.findAll().stream()
                .map(this::convertToCourseDetailDto)
                .toList();
    }

    @Override
    public CourseDetailDto findById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(CourseException.NotFoundException::new);
        return convertToCourseDetailDto(course);
    }

    @Override
    public CourseDetailDto add(CourseDto courseDto) {
        Course course = new Course();
        course.setName(courseDto.getName());

        CourseDetail courseDetail = CourseDetail.builder()
                .courseDescription(courseDto.getCourseDescription())
                .credit(courseDto.getCredit())
                .program(courseDto.getProgram())
                .lastUpdated(courseDto.getLastUpdated())
                .course(course)
                .build();
        course.setCourseDetails(courseDetail);

        courseRepository.save(course);
        return convertToCourseDetailDto(course);
    }

    @Override
    public CourseDetailDto delete(Long id) {
        CourseDetailDto course = findById(id);
        courseRepository.deleteById(id);
        return course;
    }

    @Override
    public CourseDetailDto update(Long id, CourseDto updatedDto) {
        Course course = courseRepository.findById(id).orElseThrow(CourseException.NotFoundException::new);

        if (updatedDto.getName() != null) {
            course.setName(updatedDto.getName());
        }

        CourseDetail courseDetail = course.getCourseDetails();
        if (updatedDto.getCourseDescription() != null) {
            courseDetail.setCourseDescription(updatedDto.getCourseDescription());
        }
        if (updatedDto.getCredit() != null) {
            courseDetail.setCredit(updatedDto.getCredit());
        }
        if (updatedDto.getProgram() != null) {
            courseDetail.setProgram(updatedDto.getProgram());
        }
        if (updatedDto.getLastUpdated() != null) {
            courseDetail.setLastUpdated(updatedDto.getLastUpdated());
        }

        courseRepository.save(course);
        return convertToCourseDetailDto(course);
    }

    private CourseDetailDto convertToCourseDetailDto(Course course) {
        CourseDetail courseDetail = course.getCourseDetails();
        return CourseDetailDto.builder()
                .id(course.getId())
                .name(course.getName())
                .courseDescription(courseDetail.getCourseDescription())
                .credit(courseDetail.getCredit())
                .program(courseDetail.getProgram())
                .lastUpdated(courseDetail.getLastUpdated())
                .build();
    }
}
