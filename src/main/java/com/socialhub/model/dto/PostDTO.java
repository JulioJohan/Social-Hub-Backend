package com.socialhub.model.dto;

import java.io.Serializable;

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
}
