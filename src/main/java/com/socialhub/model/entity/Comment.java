package com.socialhub.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_comment") 
	private Integer idComment;
	
	@Column(name = "descripcion",nullable = false)
	private String descripcion;
	
	@Column(name = "num_like",nullable = false)
	private Integer numLike;
	
	@Column(name = "multimedia",nullable = true)
	private String multimedia;
	
	@ManyToOne
	@JoinColumn(name = "id_user",nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_post",nullable = false)
	private Post post;
	

}
