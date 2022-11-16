package com.deliveryapi.exceptionhandler;

import com.deliveryapi.domain.exception.DomainException;
import com.deliveryapi.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Error.Field> fields = ex.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> Error.Field.builder()
                    .name(((FieldError) objectError).getField())
                    .message(messageSource.getMessage(objectError, LocaleContextHolder.getLocale()))
                    .build())
                .collect(Collectors.toList());

        Error e = Error.builder()
                .status(status.value())
                .dateTime(OffsetDateTime.now())
                .title("One or more fields are invalid. Please try again")
                .fields(fields)
                .build();

        return handleExceptionInternal(ex, e, headers, status, request);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException exception, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Error error = Error.builder()
                .status(status.value())
                .dateTime(OffsetDateTime.now())
                .title(exception.getMessage())
                .build();

        return handleExceptionInternal(exception, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Error error = Error.builder()
                .status(status.value())
                .dateTime(OffsetDateTime.now())
                .title(exception.getMessage())
                .build();

        return handleExceptionInternal(exception, error, new HttpHeaders(), status, request);
    }
}
