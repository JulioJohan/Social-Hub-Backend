package com.socialhub.controller;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.socialhub.service.ICommentService;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

	@InjectMocks
	CommentController commentController;
	
	@Mock
	ICommentService commentService;
	
	
	@Test
	void findAllPostTest(){
		commentController.findAllPost();		
	}
	
	@Test
	void findAllPostPageTest(){
		commentController.findAllPost(10, 10);
	}
	
	@Test
	void findByIdCommentTest(){
		commentController.findByIdComment(Mockito.anyInt());
	}
	
	@Test
	void findByPostCommentTest() {
		commentController.findByPostComment(Mockito.anyInt());
	}
	
	@Test
	void createCommentTest() throws IOException {
		commentController.createComment(Mockito.any());
	}
	
	@Test
	void updateCommentTest() throws IOException {
		commentController.updateComment(Mockito.any());
	}
	
	@Test
	void deleteCommentTest() {
		commentController.deleteComment(Mockito.any());
	}
	
	@Test
	void sumLikeTest() {
		commentController.sumLike(Mockito.any());
	}
	
	@Test
	void subtractLikeTest() {
		commentController.subtractLike(Mockito.any());
	}
	
		
}
