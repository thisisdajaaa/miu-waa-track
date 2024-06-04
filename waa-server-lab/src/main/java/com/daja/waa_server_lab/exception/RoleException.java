package com.daja.waa_server_lab.exception;

public class RoleException {
    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {
            super("Role not found!");
        }

        public NotFoundException(String message) {
            super(message);
        }
    }

}
