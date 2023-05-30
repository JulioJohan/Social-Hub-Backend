package com.socialhub.model.entity;

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
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_post",nullable = false)
	private Integer idPost;
	
	@Column(name = "description",nullable = false)
	private String description;

	@Column(name = "num_like",nullable = false)
	private Integer numLike;
	
	//Se pone como string porque se estaria consumiendo en AWS
	@Column(name = "multimeda",nullable = true)
	private String multimedia;
	
	@Column(name = "share",nullable = false)
	private String share;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user",nullable = false)
	private User user;
	

}
