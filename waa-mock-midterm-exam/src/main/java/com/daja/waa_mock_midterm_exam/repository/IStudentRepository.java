package com.daja.waa_mock_midterm_exam.repository;

import com.daja.waa_mock_midterm_exam.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByGpaLessThanEqual(double gpa);

    @Query("SELECT s FROM Student s JOIN s.courses c WHERE s.gpa < :gpa AND c.courseDetails.program = :program")
    List<Student> findByGpaLessThanAndCourseProgram(double gpa, String program);
}