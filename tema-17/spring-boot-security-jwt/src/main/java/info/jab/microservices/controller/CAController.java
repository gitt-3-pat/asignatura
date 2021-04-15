package info.jab.microservices.controller;

import info.jab.microservices.model.GlobalErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CAController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {

        LOGGER.error(ex.getLocalizedMessage(), ex);

        if (ex instanceof BadCredentialsException) {
            String message = "Bad Credentials";
            return handleExceptionInternal(ex,
                    new GlobalErrorResponse(message),
                    new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
        } else {
            String message = "Contact with the operator";
            return handleExceptionInternal(ex,
                    new GlobalErrorResponse(message),
                    new HttpHeaders(),
                    HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }
}