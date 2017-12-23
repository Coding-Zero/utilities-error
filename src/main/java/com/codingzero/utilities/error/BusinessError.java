package com.codingzero.utilities.error;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BusinessError extends RuntimeException {

    private ErrorType type;
    private Map<String, Object> details;

    protected BusinessError(ErrorType type, Map<String, Object> details) {
        this("[" + type.toString() + "]", type, details);
    }

    protected BusinessError(String message, ErrorType type, Map<String, Object> details) {
        super(message);
        this.type = type;
        this.details = Collections.unmodifiableMap(details);
    }

    public ErrorType getType() {
        return type;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public static BusinessErrorBuilder raise(ErrorType type) {
        return new BusinessError.BusinessErrorBuilder(type);
    }

    public static BusinessErrorBuilder noSuchEntityFound() {
        return new BusinessError.BusinessErrorBuilder(DefaultErrors.NO_SUCH_ENTITY_FOUND);
    }

    public static class BusinessErrorBuilder {

        private ErrorType type;
        private String message;
        private Map<String, Object> details;

        private BusinessErrorBuilder(ErrorType type) {
            this.type = type;
            this.message = null;
            this.details = new HashMap<>();
        }

        public BusinessErrorBuilder message(String message) {
            this.message = message;
            return this;
        }

        public BusinessErrorBuilder details(String key, Object value) {
            details.put(key, value);
            return this;
        }

        public ErrorType getType() {
            if (null == type) {
                throw new IllegalArgumentException("Business error type is required.");
            }
            return type;
        }

        public String getMessage() {
            return message;
        }

        public Map<String, Object> getDetails() {
            return details;
        }

        public BusinessError build() {
            if (getMessage() == null) {
                return new BusinessError(getType(), getDetails());
            }
            return new BusinessError(getMessage(), getType(), getDetails());
        }

    }

    public enum DefaultErrors implements ErrorType {

        NO_SUCH_ENTITY_FOUND("NoSuchEntityFoundError");

        private final String name;

        DefaultErrors(String name) {
            this.name = name;
        }

        public String toString() {
            return this.getName();
        }

        @Override
        public String getName() {
            return name;
        }

    }

}
