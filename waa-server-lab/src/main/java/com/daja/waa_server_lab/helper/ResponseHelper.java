package com.daja.waa_server_lab.helper;

import lombok.Data;

import java.util.Map;

public class ResponseHelper {
    @Data
    public static class CustomResponse<T> {
        private Boolean success;
        private String message;
        private T data;
        private Map<String, String> errors;

        public CustomResponse(Boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
            this.errors = null;
        }

        public CustomResponse(Boolean success, String message, T data, Map<String, String> errors) {
            this.success = success;
            this.message = message;
            this.data = data;
            this.errors = errors;
        }
    }
}
