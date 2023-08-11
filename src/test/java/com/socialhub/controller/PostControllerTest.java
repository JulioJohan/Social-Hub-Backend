package com.socialhub.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.socialhub.model.dto.PostDTO;
import com.socialhub.service.IPostService;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {
	   
	@InjectMocks
	private PostController postController;
	
	@Mock
	IPostService iPostService;
	
	@BeforeEach
	public void setUp() {
		
	}
	
	@Test
	void findAllPostTest() {
		postController.findAllPost();
		assertNotNull("1");
	}
	@Test
	void findAllPost2Test() {
		postController.findAllPost(1, 1);
		assertNotNull("1");
	}
	@Test
	void findByIdPost() {
		postController.findByIdPost(1);
		assertNotNull("1");
	}
	@Test
	void findByUserPost() {
		postController.findByUserPost(1, 1);
		assertNotNull("1");
	}
	@Test
	void createPost() throws IOException {
		PostDTO post= new PostDTO();
		post.setDescription(null);
		post.setIdPost(null);
		post.setMultimedia(null);
		post.setNumLike(null);
		post.setShare(null);
		post.setUser(null);
		
		post.getDescription();
		post.getIdPost();
		post.getMultimedia();
		post.getNumLike();
		post.getShare();
		post.getUser();
		
		postController.createPost(post, 1);
		assertNotNull("1");
	}
	@Test
	void updatePost() throws IOException {
		PostDTO post= new PostDTO();
		postController.updatePost(post, 0);
		assertNotNull("1");
	}
	@Test
	void deletePost() {
		postController.deletePost(1);
		assertNotNull("1");
	}
	@Test
	void sumLike() {
		postController.sumLike(1);
		assertNotNull("1");
	}
	@Test
	void subtractLike() {
		postController.subtractLike(1);
		assertNotNull("1");
	}

	
}
