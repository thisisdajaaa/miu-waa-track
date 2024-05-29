package com.daja.waa_server_lab.exception;

public class PostException {
    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {super("Post not found!");}

        public NotFoundException(String message) {super(message);}
    }

}
