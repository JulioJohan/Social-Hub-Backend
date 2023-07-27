package com.socialhub.model.entity;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentTest {

	@Test
	void setUp() {
		Comment comment = new Comment();
		comment.setDateRegistration(new Date());
		comment.setDescripcion("Descripcion");
		comment.setIdComment(1);
		comment.setMultimedia("multimedia");
		comment.setNumLike(1);
		comment.setPost(new Post());
		comment.setUser(new User());
		
		
		comment.getDateRegistration();
		comment.getDescripcion();
		comment.getIdComment();
		comment.getMultimedia();
		comment.getNumLike();
		comment.getPost();
		comment.getUser();
	}

}
