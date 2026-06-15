package tickethub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.OptimisticLockException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleOptimisticLock() {
        return "Event was updated by another user. Try again.";
    }
}