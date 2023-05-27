package com.socialhub.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user",nullable = false)
	private Integer idUser;
	
	@Column(name = "name",nullable = false)
	private String name;
	
	@Column(name = "email",nullable = false,unique = true)
	private String email;
	
	@Column(name = "password",nullable = false)
	private String password;
	
	@Column(name = "confirmed",nullable =  false)
	private Boolean confirmed;
	
	@Column(name = "email_verified",nullable = false)
	private String emailVerified;
	
	@Column(name = "multi_factor_authentication",nullable = false)
	private String multiFactorAuthentication;
	
	@Column(name = "father_last_name", nullable = true)
	private String fatherLastName;
	
	@Column(name = "mother_last_name", nullable = true)
	private String motherLastName;
	
	@Column(name = "age",nullable = true)
	private Integer age;
	
	@Column(name = "date_birth", nullable = true)
	private Date dateBirth;
	
	//Se pone como string porque se estaria consumiendo en AWS
	@Column(name = "avatar",nullable = true)
	private String avatar;
	
	
	//Poniendo el contructor solo para que cree la cuenta se establesca 
	// lo pone como false
	public User() {
		this.confirmed = false;
	}
	
}
