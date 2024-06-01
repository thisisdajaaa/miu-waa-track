package com.daja.waa_mock_midterm_exam.exception;

public class StudentException {
    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {super("Student not found!");}

        public NotFoundException(String message) {super(message);}
    }
}
