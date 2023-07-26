package com.socialhub.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_registration", nullable = false)
	private Date dateRegistration;

	@PrePersist
	private void onCreate() {
		dateRegistration = new Date();
	}

}
