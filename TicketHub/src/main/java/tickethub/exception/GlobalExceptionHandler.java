package tickethub.exception;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public String handleOptimisticLock() {
        return "redirect:/?error=concurrency";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntime(RuntimeException ex) {
        return "redirect:/?error=" + ex.getMessage();
    }
}