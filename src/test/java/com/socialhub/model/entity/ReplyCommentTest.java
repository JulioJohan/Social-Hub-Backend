package com.socialhub.model.entity;

import java.util.Date;

import javax.persistence.PrePersist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReplyCommentTest {

	@InjectMocks
	private ReplyComment myEntity;
	
	@Test
	void setUp() {		
		ReplyComment reply = new ReplyComment();
		reply.setDateRegistration(new Date());
		reply.setDescripcion("Descripcion");
		reply.setIdReplyComment(1);
		reply.setMultimedia("Multimedia");
		reply.setNumLike(1);
		reply.setUser(new User());
		
		
		reply.getComment();
		reply.getDateRegistration();
		reply.getDescripcion();
		reply.getIdReplyComment();
		reply.getMultimedia();
		reply.getNumLike();
		reply.getUser();		
		
	}
}
