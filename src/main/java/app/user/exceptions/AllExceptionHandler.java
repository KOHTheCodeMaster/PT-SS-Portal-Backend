package app.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(CorrugationException.class)
    public ResponseEntity<ExceptionResponse> corrugationNotFound(CorrugationException e) {

        ExceptionResponse response = new ExceptionResponse();
        response.setError("NOT_FOUND");
        response.setMessage(e.getMessage());
        response.setStatus(404);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ProductionException.class)
    public ResponseEntity<ExceptionResponse> productionNotFound(ProductionException e) {

        ExceptionResponse response = new ExceptionResponse();
        response.setError("NOT_FOUND");
        response.setMessage(e.getMessage());
        response.setStatus(404);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(SalesException.class)
    public ResponseEntity<ExceptionResponse> salesNotFound(SalesException e) {

        ExceptionResponse response = new ExceptionResponse();
        response.setError("NOT_FOUND");
        response.setMessage(e.getMessage());
        response.setStatus(404);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ExceptionResponse> invalidNumberFormatException(NumberFormatException e) {

        ExceptionResponse response = new ExceptionResponse();
        response.setError("BAD REQUEST");
        response.setMessage("Invalid URL Parameter Type. Required: Integer  |  Found: String");
        response.setStatus(400);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

}
