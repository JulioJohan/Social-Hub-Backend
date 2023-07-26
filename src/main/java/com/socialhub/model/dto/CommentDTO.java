package com.socialhub.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CommentDTO implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private Integer idComment;

    private String descripcion;

    private Integer numLike;

    private String multimedia;

    private Integer user;

    private Integer post;

    @Transient
    private transient MultipartFile multipartFile;

    // Getters y Setters generados automáticamente por Lombok


}
