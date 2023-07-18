package com.socialhub.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "reply_comment")
public class ReplyComment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_registration", nullable = false)
	private Date dateRegistration;

	@PrePersist
	private void onCreate() {
		dateRegistration = new Date();
	}
	
}
