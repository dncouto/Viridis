package energy.viridis.exercise.error;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RequestExceptionHandler extends ExceptionHandlerExceptionResolver{

	@ExceptionHandler({ NoSuchElementException.class })
	public ResponseEntity<Object> notFoundError(Exception ex) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> updateConstraintError(ConstraintViolationException ex) {

        log.info(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Unfilled field(s): " + ex.getConstraintViolations().stream().map(constraint -> constraint.getPropertyPath().toString()).collect(Collectors.joining(", ")) + ".");
    }
	
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> deleteConstraintError(DataIntegrityViolationException ex) {

        log.info(ex.getMessage());
        
        String msgError = ex.getMessage();
        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            msgError = ((org.hibernate.exception.ConstraintViolationException)ex.getCause()).getSQLException().getMessage();
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msgError);
	}
	
	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> generalError(Exception ex) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}

}
