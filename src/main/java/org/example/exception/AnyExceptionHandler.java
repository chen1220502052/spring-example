package org.example.exception;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class AnyExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(AnyExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletResponse response){

        return null;
    }
}
