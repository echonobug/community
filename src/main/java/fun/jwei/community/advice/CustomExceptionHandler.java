package fun.jwei.community.advice;

import fun.jwei.community.exception.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    String handle(CustomException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

}
