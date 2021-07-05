package app.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@CrossOrigin
@ControllerAdvice
public class AllExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllExceptionHandler.class);

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

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionResponse> userNotFound(UserException e) {

        ExceptionResponse response = new ExceptionResponse();
        response.setError("NOT_FOUND");
        response.setMessage(e.getMessage());
        response.setStatus(404);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(TargetException.class)
    public ResponseEntity<ExceptionResponse> targetNotFound(TargetException e) {

        ExceptionResponse response = new ExceptionResponse();
        response.setError("NOT_FOUND");
        response.setMessage(e.getMessage());
        response.setStatus(402);
        response.setTimestamp(LocalDateTime.now());

        LOGGER.error("Target Exception Response: " + response);

        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

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
