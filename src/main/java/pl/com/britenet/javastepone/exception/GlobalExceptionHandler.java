package pl.com.britenet.javastepone.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.util.Collections.emptyList;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorDetails resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        logException(ex);
        return new ErrorDetails(ex.getMessage(), request.getDescription(false), emptyList());
    }

    @ExceptionHandler(ResourceFoundException.class)
    public ErrorDetails resourceFoundException(ResourceFoundException ex, WebRequest request) {
        logException(ex);
        return new ErrorDetails(ex.getMessage(), request.getDescription(false), emptyList());
    }

    @ExceptionHandler(ValidationException.class)
    public ErrorDetails validationException(ValidationException ex, WebRequest request) {
        logException(ex);
        return new ErrorDetails(ex.getMessage(), request.getDescription(false), ex.getFieldErrorList());
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorDetails globalExceptionHandler(Exception ex, WebRequest request) {
        logException(ex);
        return new ErrorDetails(ex.getMessage(), request.getDescription(false), emptyList());
    }

    private void logException(Exception exception) {
        logger.error("Exception thrown: {}#{} on line: {} with message: {}",
                exception.getStackTrace()[0].getClassName(),
                exception.getClass().getSimpleName(),
                exception.getStackTrace()[0].getLineNumber(),
                exception.getMessage());

        logger.debug("Exception details", exception);
    }
}