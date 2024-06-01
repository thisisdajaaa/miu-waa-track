package com.daja.waa_mock_midterm_exam.exception;

public class CourseException {
    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {super("Course not found!");}

        public NotFoundException(String message) {super(message);}
    }
}
