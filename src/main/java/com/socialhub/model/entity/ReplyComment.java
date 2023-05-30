package com.socialhub.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "reply_comment")
public class ReplyComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reply_comment")
	private Integer idReplyComment;
	
	@Column(name = "descripcion",nullable = false)
	private String descripcion;
	
	@Column(name = "multimedia",nullable = true)
	private String multimedia;
	
	@Column(name = "num_like",nullable = false)
	private Integer numLike;
	
	@ManyToOne
	@JoinColumn(name = "id_user",nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "id_comment",nullable = false)
	private Comment comment;
	
	
}
