package pl.com.britenet.javastepone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    private final List<ValidationError> validationErrorList;

    public ValidationException(String message, List<ValidationError> validationErrorList) {
        super(message);
        this.validationErrorList = validationErrorList;
    }

    public List<ValidationError> getFieldErrorList() {
        return validationErrorList;
    }
}
