package org.example.exception;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.Charset;

@ControllerAdvice
public class AnyExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(AnyExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletResponse response){
        logger.error(e.getMessage());
        response.setCharacterEncoding("UTF-8");
        try(PrintWriter writer = response.getWriter()){
            JsonGenerator jg = new JsonFactory().createGenerator(writer);
            jg.writeStartObject();
            jg.writeNumberField("code", 503);
            jg.writeStringField("message", "服务器内部错误");
            jg.writeEndObject();
            jg.close();
            writer.flush();
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return null;
    }
}
