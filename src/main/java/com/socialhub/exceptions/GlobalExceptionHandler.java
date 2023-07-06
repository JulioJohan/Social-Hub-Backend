package com.socialhub.exceptions;

import com.socialhub.model.entity.Comment;
import com.socialhub.model.entity.Post;
import com.socialhub.model.entity.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<Comment>> handlePostBusinessException(BusinessException ex) {
        Response<Comment> errorResponse = new Response<>();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getHttpEstatus());
    }




    // Agrega más métodos para otros tipos de respuestas que necesites manejar
}
