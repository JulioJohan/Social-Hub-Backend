package com.socialhub.model.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class PostDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idPost;
	
	private String description;

	private Integer numLike;
	
	private String multimedia;
	
	private String share;
	
	private Integer user;
	
	@Transient
	private transient MultipartFile multipartFile;
	
    // Getters y Setters generados automáticamente por Lombok

}
