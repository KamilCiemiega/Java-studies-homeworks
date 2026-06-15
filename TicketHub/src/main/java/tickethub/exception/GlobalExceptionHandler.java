package tickethub.exception;

import jakarta.persistence.OptimisticLockException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OptimisticLockException.class)
    public String handleOptimisticLock() {
        return "redirect:/events?error=concurrency";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntime(RuntimeException ex) {
        return "redirect:/events?error=" + ex.getMessage();
    }
}