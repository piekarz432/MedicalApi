package pl.com.medicalApi.exception;

public class ValidationError {

    private final String fieldName;
    private final String details;
    private final String pattern;

    public ValidationError(String fieldName, String details, String pattern) {
        this.fieldName = fieldName;
        this.details = details;
        this.pattern = pattern;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getDetails() {
        return details;
    }

    public String getPattern() {
        return pattern;
    }
}
