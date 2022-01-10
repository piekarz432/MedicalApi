package pl.com.britenet.javastepone.exception;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.now;

public class ErrorDetails {

    private final LocalDate timestamp;
    private final String message;
    private final String details;
    private final List<ValidationError> validationErrorList;

    public ErrorDetails(String message, String details, List<ValidationError> validationErrorList) {
        this.timestamp = now();
        this.message = message;
        this.details = details;
        this.validationErrorList = validationErrorList;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public List<ValidationError> getFieldErrorList() {
        return validationErrorList;
    }
}
