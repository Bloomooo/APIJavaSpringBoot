package Controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.webcompany.entites.UserException;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class ApplicationControllerAdvise {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public @ResponseBody UserException handlUserException(EntityNotFoundException e) {
        return new UserException(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody UserException handlRuntimeExceptionException(RuntimeException e) {
        return new UserException(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(Exception.class)
    public @ResponseBody UserException handlAlreadyInDatabaseException(Exception e) {
        return new UserException(HttpStatus.CONFLICT, e.getMessage());
    }
}
