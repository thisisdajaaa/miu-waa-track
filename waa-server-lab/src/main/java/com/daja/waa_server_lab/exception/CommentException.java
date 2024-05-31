package com.daja.waa_server_lab.exception;

public class CommentException {
    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {
            super("Comment not found!");
        }

        public NotFoundException(String message) {
            super(message);
        }
    }

}
