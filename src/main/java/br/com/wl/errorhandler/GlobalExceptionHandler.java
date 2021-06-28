package br.com.wl.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String NOT_FOUND_MESSAGE = "No resource found.";
    private static final String NULLPOINTER_MESSAGE = "Nullpointer.";

    /**
     * Handling 404 error
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<VndError> noHandlerFound(HttpServletRequest request, NoHandlerFoundException ex) {
        ErrorMessage errorMessage = createErrorMessage(ErrorType.NOT_FOUND, NOT_FOUND_MESSAGE, null);
        VndError vndError = new VndError();
        vndError.setErrors(Collections.singletonList(errorMessage));
        vndError.setTimestamp(new Date().getTime());
        vndError.setUri(getRequestUri(request));
        return new ResponseEntity<VndError>(vndError, HttpStatus.NOT_FOUND);
    }

    /**
     * Handling Validation Error
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<VndError> validationError(HttpServletRequest request, ConstraintViolationException ex) {
        List<ErrorMessage> errors = ex.getConstraintViolations().stream().map(constraintViolation -> {
            String meta = Optional.ofNullable(constraintViolation.getPropertyPath())
                    .map(Path::toString)
                    .orElse(null);
            return createErrorMessage(ErrorType.VALIDATION_ERROR, constraintViolation.getMessage(), meta);
        }).collect(Collectors.toList());
        VndError vndError = new VndError();
        vndError.setErrors(errors);
        vndError.setTimestamp(new Date().getTime());
        vndError.setUri(getRequestUri(request));
        return new ResponseEntity<VndError>(vndError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Invalid Argument Error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<VndError> methodArgumentError(HttpServletRequest request, MethodArgumentNotValidException ex) {
        List<ErrorMessage> errors = ex.getAllErrors().stream().map( error -> {
            String meta = Optional.ofNullable(error.getArguments())
                    .filter(a -> a != null && a.length > 0)
                    .map(arguments -> {
                        final Object resolvable = arguments[0];
                        if (resolvable instanceof DefaultMessageSourceResolvable) {
                            final String field = ((DefaultMessageSourceResolvable) resolvable).getDefaultMessage();
                            return Optional.ofNullable(field).orElse(null);
                        }
                        return null;
                    }).orElse(null);
            return createErrorMessage(ErrorType.VALIDATION_ERROR, error.getDefaultMessage(), meta);
        }).collect(Collectors.toList());
        VndError vndError = new VndError();
        vndError.setErrors(errors);
        vndError.setTimestamp(new Date().getTime());
        vndError.setUri(getRequestUri(request));
        return new ResponseEntity<VndError>(vndError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handling API Generic Error
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<VndError> apiInternalError(HttpServletRequest request, Throwable ex) {
        log.error("Generic Error", ex);
        ErrorMessage errorMessage = createErrorMessage(
                ErrorType.INTERNAL_ERROR,
                Optional.ofNullable(ex.getMessage()).orElse(NULLPOINTER_MESSAGE),
                null
        );
        VndError vndError = new VndError();
        vndError.setErrors(Collections.singletonList(errorMessage));
        vndError.setTimestamp(new Date().getTime());
        vndError.setUri(getRequestUri(request));
        return new ResponseEntity<VndError>(vndError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorMessage createErrorMessage(ErrorType error, String message, String meta) {
        return new ErrorMessage(error, message, meta );
    }

    private String getRequestUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
