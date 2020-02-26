package by.viktorff.newsfeed.controller.exception;

import by.viktorff.newsfeed.exception.user.AuthenticationUserException;
import by.viktorff.newsfeed.exception.user.DeleteUserException;
import by.viktorff.newsfeed.exception.user.LoginUserException;
import by.viktorff.newsfeed.exception.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class UserExceptionController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("User not valid", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFound() {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationUserException.class)
    public ResponseEntity<String> authEx(AuthenticationUserException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginUserException.class)
    public ResponseEntity<String> userLoginEx() {
        return new ResponseEntity<>("User is not logged in", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeleteUserException.class)
    public ResponseEntity<String> userDeleteEx() {
        return new ResponseEntity<>("Cannot delete user. User is logged in now", HttpStatus.BAD_REQUEST);
    }
}
