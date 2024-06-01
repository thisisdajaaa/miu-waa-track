package com.daja.waa_mock_midterm_exam.exception;

public class AddressException {
    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {super("Address not found!");}

        public NotFoundException(String message) {super(message);}
    }
}
