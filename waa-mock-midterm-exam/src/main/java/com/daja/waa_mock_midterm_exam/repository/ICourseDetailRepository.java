package com.daja.waa_mock_midterm_exam.repository;

import com.daja.waa_mock_midterm_exam.entity.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseDetailRepository extends JpaRepository<CourseDetail, Long> {
}
