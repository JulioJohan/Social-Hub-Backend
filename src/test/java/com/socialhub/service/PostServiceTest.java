package com.socialhub.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import com.socialhub.model.dto.PostDTO;
import com.socialhub.model.entity.Post;
import com.socialhub.model.entity.Response;
import com.socialhub.model.entity.User;
import com.socialhub.repository.IPostRepository;
import com.socialhub.repository.IUserRepository;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
	@InjectMocks
	private PostService postService;
	
	@Mock
	IPostRepository iPostRepository;
	
	@Mock
	IUserRepository iUserRepository;
	
	@BeforeEach
	public void setUp() {
		
	}
	
	@Test
	void findAllPostTest() {
		Response<Post> response=postService.findAllPost();
		assertNotNull(response);	
	}
	
	@Test
	void findAllPost2Test() {
        when(iPostRepository.findAll()).thenThrow(new DataAccessException("Simulated DataAccessException") {});
        Response<Post> response=postService.findAllPost();
		assertNotNull(response);	
	}
	
	
	@Test
	void findByIdPost() {
//        when(iPostRepository.findAll()).thenThrow(new DataAccessException("Simulated DataAccessException") {});
		Response<Post> response= postService.findByIdPost(1);
		assertNotNull(response);
	}
	
	@Test
	void findByIdPost2() {
        when(iPostRepository.findById(Mockito.any())).thenReturn(Optional.of(new Post()));
        Response<Post> response=postService.findByIdPost(1);
		assertNotNull(response);	
	}
	
	@Test
	void findByIdPost3() {
        when(iPostRepository.findById(Mockito.any())).thenThrow(new DataAccessException("Simulated DataAccessException") {});
        Response<Post> response=postService.findByIdPost(1);
		assertNotNull(response);	
	}
	
	@Test
	void findByUserPostTest() {
		Response<Post> response= postService.findByUserPost(1);
		assertNotNull(response);
	}
	
	@Test
	void findByUserPost2Test() {
        when(iPostRepository.findByUserIdUser(Mockito.any())).thenThrow(new DataAccessException("Simulated DataAccessException") {});
		Response<Post> response= postService.findByUserPost(1);
		assertNotNull(response);
	}
	
	@Test
	void createPostTest() {
		PostDTO post = new PostDTO();
		post.setUser(1);
		when(iUserRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new User()));
		Response<Post> response= postService.createPost(post);
		assertNotNull(response);
	}
	
	@Test
	void createPost2Test() {
		PostDTO post = new PostDTO();
		post.setUser(1);
//		when(iUserRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new User()));
		Response<Post> response= postService.createPost(post);
		assertNotNull(response);
	}
	
	@Test
	void createPost3Test() {
		PostDTO post = new PostDTO();
		post.setUser(1);
		when(iUserRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new User()));
        when(iPostRepository.save(Mockito.any())).thenThrow(new DataAccessException("Simulated DataAccessException") {});
		Response<Post> response= postService.createPost(post);
		assertNotNull(response);
	}
	
	@Test
	void updatePostTest() {
		PostDTO post = new PostDTO();
		post.setUser(1);
		Response<Post> response= postService.updatePost(post);
		assertNotNull(response);
	}
	
	@Test
	void updatePost2Test() {
		PostDTO post = new PostDTO();
		post.setIdPost(1);
		when(iPostRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Post()));
		Response<Post> response= postService.updatePost(post);
		assertNotNull(response);
	}
	
	@Test
	void updatePost3Test() {
		PostDTO post = new PostDTO();
		post.setIdPost(1);
		when(iPostRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Post()));
        when(iPostRepository.save(Mockito.any())).thenThrow(new DataAccessException("Simulated DataAccessException") {});

		Response<Post> response= postService.updatePost(post);
		assertNotNull(response);
	}
	
	@Test
	void deletePostTest(){
		Response<Post> response= postService.deletePost(1);
		assertNotNull(response);
	}
	
	@Test
	void deletePost2Test(){
        doThrow(new DataAccessException("Simulated DataAccessException") {}).when(iPostRepository).deleteById(1);

		Response<Post> response= postService.deletePost(1);
		assertNotNull(response);
	}
	
	@Test
	void sumLikeTest(){
		Response<Post> response= postService.sumLike(1);
		assertNotNull(response);
	}
	
	@Test
	void sumLike2Test(){
		when(iPostRepository.findById(Mockito.any())).thenReturn(Optional.of(new Post()));
		Response<Post> response= postService.sumLike(1);
		assertNotNull(response);
	}
	
	@Test
	void sumLike3Test(){
		when(iPostRepository.findById(Mockito.any())).thenReturn(Optional.of(new Post()));
		doThrow(new DataAccessException("Simulated DataAccessException") {}).when(iPostRepository).sumLike(Mockito.any());
		Response<Post> response= postService.sumLike(1);
		assertNotNull(response);
	}
	
	@Test
	void subtractLikeTest(){
		Response<Post> response= postService.subtractLike(1);
		assertNotNull(response);
	}
	
	@Test
	void subtractLike2Test(){
		when(iPostRepository.findById(Mockito.any())).thenReturn(Optional.of(new Post()));
		Response<Post> response= postService.subtractLike(1);
		assertNotNull(response);
	}
	
	@Test
	void subtractLike3Test(){
		when(iPostRepository.findById(Mockito.any())).thenReturn(Optional.of(new Post()));
		doThrow(new DataAccessException("Simulated DataAccessException") {}).when(iPostRepository).subtractLike(Mockito.any());
		Response<Post> response= postService.subtractLike(1);
		assertNotNull(response);
	}
	
}
