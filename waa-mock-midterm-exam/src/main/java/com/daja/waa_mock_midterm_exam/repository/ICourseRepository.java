package com.daja.waa_mock_midterm_exam.repository;

import com.daja.waa_mock_midterm_exam.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Long> {
}
