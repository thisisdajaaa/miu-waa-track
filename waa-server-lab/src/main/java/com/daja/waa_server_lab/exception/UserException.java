package com.daja.waa_server_lab.exception;

public class UserException {
    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {super("User not found!");}

        public NotFoundException(String message) {super(message);}
    }

}
