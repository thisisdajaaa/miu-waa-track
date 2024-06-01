package com.daja.waa_mock_midterm_exam.service.impl;

import com.daja.waa_mock_midterm_exam.configuration.MapperConfiguration;
import com.daja.waa_mock_midterm_exam.entity.Address;
import com.daja.waa_mock_midterm_exam.entity.Course;
import com.daja.waa_mock_midterm_exam.entity.Student;
import com.daja.waa_mock_midterm_exam.entity.dto.request.StudentDto;
import com.daja.waa_mock_midterm_exam.entity.dto.response.CourseDetailDto;
import com.daja.waa_mock_midterm_exam.entity.dto.response.StudentDetailDto;
import com.daja.waa_mock_midterm_exam.exception.AddressException;
import com.daja.waa_mock_midterm_exam.exception.CourseException;
import com.daja.waa_mock_midterm_exam.exception.StudentException;
import com.daja.waa_mock_midterm_exam.repository.IAddressRepository;
import com.daja.waa_mock_midterm_exam.repository.ICourseRepository;
import com.daja.waa_mock_midterm_exam.repository.IStudentRepository;
import com.daja.waa_mock_midterm_exam.service.spec.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements IStudentService {
    private final IStudentRepository studentRepository;
    private final IAddressRepository addressRepository;
    private final ICourseRepository courseRepository;
    private final MapperConfiguration mapperConfiguration;

    @Autowired
    public StudentServiceImpl(IStudentRepository studentRepository, IAddressRepository addressRepository, ICourseRepository courseRepository, MapperConfiguration mapperConfiguration) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.courseRepository = courseRepository;
        this.mapperConfiguration = mapperConfiguration;
    }

    @Override
    public List<StudentDetailDto> findAll(Map<String, String> filters) {
        return studentRepository.findAll().stream()
                .map(this::convertToStudentDetailDto)
                .toList();
    }

    @Override
    public StudentDetailDto findById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(StudentException.NotFoundException::new);
        return convertToStudentDetailDto(student);
    }

    @Override
    public StudentDetailDto add(StudentDto studentDto) {
        Student student = mapperConfiguration.convert(studentDto, Student.class);

        Address address = addressRepository.findById(studentDto.getAddressId())
                .orElseThrow(AddressException.NotFoundException::new);
        student.setAddress(address);

        Set<Course> courses = getCoursesFromIds(studentDto.getCourseIds());
        student.setCourses(courses);

        studentRepository.save(student);
        return convertToStudentDetailDto(student);
    }

    @Override
    public StudentDetailDto delete(Long id) {
        StudentDetailDto student = findById(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public StudentDetailDto update(Long id, StudentDto updatedDto) {
        Student student = studentRepository.findById(id).orElseThrow(StudentException.NotFoundException::new);

        if (updatedDto.getName() != null)
            student.setName(updatedDto.getName());
        if (updatedDto.getGpa() != 0)
            student.setGpa(updatedDto.getGpa());
        if (updatedDto.getAddressId() != null) {
            Address address = addressRepository.findById(updatedDto.getAddressId())
                    .orElseThrow(AddressException.NotFoundException::new);
            student.setAddress(address);
        }
        if (updatedDto.getCourseIds() != null && !updatedDto.getCourseIds().isEmpty()) {
            Set<Course> courses = getCoursesFromIds(updatedDto.getCourseIds());
            student.setCourses(courses);
        }

        studentRepository.save(student);
        return convertToStudentDetailDto(student);
    }

    @Override
    public List<CourseDetailDto> findCoursesByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(StudentException.NotFoundException::new);
        return student.getCourses().stream()
                .map(this::convertToCourseDetailDto)
                .toList();
    }

    @Override
    public List<StudentDetailDto> findStudentsByGpaLessOrEqual(double gpa) {
        return studentRepository.findByGpaLessThanEqual(gpa).stream()
                .map(this::convertToStudentDetailDto)
                .toList();
    }

    @Override
    public List<StudentDetailDto> findStudentsInMscProgramWithGpaLessThan(double gpa) {
        return studentRepository.findByGpaLessThanAndCourseProgram(gpa, "MSC").stream()
                .map(this::convertToStudentDetailDto)
                .toList();
    }

    private Set<Course> getCoursesFromIds(Set<Long> courseIds) {
        Set<Course> courses = new HashSet<>();
        for (Long courseId : courseIds) {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(CourseException.NotFoundException::new);
            courses.add(course);
        }
        return courses;
    }

    private StudentDetailDto convertToStudentDetailDto(Student student) {
        StudentDetailDto studentDetailDto = mapperConfiguration.convert(student, StudentDetailDto.class);
        Set<CourseDetailDto> courseDetailDtos = student.getCourses().stream()
                .map(this::convertToCourseDetailDto)
                .collect(Collectors.toSet());
        studentDetailDto.setCourses(courseDetailDtos);
        return studentDetailDto;
    }

    private CourseDetailDto convertToCourseDetailDto(Course course) {
        CourseDetailDto courseDetailDto = mapperConfiguration.convert(course, CourseDetailDto.class);
        if (course.getCourseDetails() != null) {
            courseDetailDto.setCourseDescription(course.getCourseDetails().getCourseDescription());
            courseDetailDto.setCredit(course.getCourseDetails().getCredit());
            courseDetailDto.setProgram(course.getCourseDetails().getProgram());
            courseDetailDto.setLastUpdated(course.getCourseDetails().getLastUpdated());
        }
        return courseDetailDto;
    }
}
