package com.Learn.ExceptionHandling.Advices;

import com.Learn.ExceptionHandling.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException exception)
    {
        ApiError apiError=ApiError.builder().status(HttpStatus.NOT_FOUND).message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllOtherExceptions(Exception e)
    {
        ApiError apiError=ApiError.builder().status(HttpStatus.BAD_REQUEST).message(e.getMessage()).build();
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<ApiError> handleInputValidationExceptions(MethodArgumentNotValidException e)
    {
        List<String> errors= e.getBindingResult().getAllErrors().stream().map(error-> error.getDefaultMessage()).collect(Collectors.toList());
        ApiError apiError= ApiError.builder().
                status(HttpStatus.BAD_REQUEST).
                message("Input validations failed because of below error")
                .subErrors(errors)
                .build();
        return  new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
    }
}
