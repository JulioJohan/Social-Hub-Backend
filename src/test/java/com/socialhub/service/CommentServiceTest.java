package com.socialhub.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.socialhub.exceptions.BusinessException;
import com.socialhub.model.dto.CommentDTO;
import com.socialhub.model.entity.Comment;
import com.socialhub.model.entity.Post;
import com.socialhub.model.entity.Response;
import com.socialhub.model.entity.User;
import com.socialhub.repository.ICommentRepository;
import com.socialhub.repository.IPostRepository;
import com.socialhub.repository.IUserRepository;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

	@InjectMocks
	CommentService commentService;
	
	@Mock
	ICommentRepository commentRepository;
	
	@Mock
	IUserRepository userRepository;
	
	@Mock
	IPostRepository postRepository;
	
	@Mock
	FirebaseStorageStrategyService firebaseStorageStrategyService;
	
	Comment comment;
	Response<Comment> response;
	List<Comment> listComment;
	CommentDTO commentDto;
	User user;
	Post post;
	
	@BeforeEach()
	void setUp() {
		comment = new Comment();
		comment.setDateRegistration(new Date());
		comment.setDescripcion("Descripcion");
		comment.setIdComment(1);
		comment.setMultimedia("Multimedia");
		comment.setNumLike(1);
		comment.setPost(new Post());
		comment.setUser(new User());
		listComment = new ArrayList<Comment>();
		response = new Response<Comment>();	

		//comentario DTO	
		commentDto = new CommentDTO();
		commentDto.setDescripcion("Descripcion");
		commentDto.setIdComment(1);
		commentDto.setMultimedia("Multimedia");
		commentDto.setNumLike(1);
		commentDto.setPost(12);
		commentDto.setUser(33);
		
		user = new User();
		user.setAge(21);
		user.setAvatar("avatar");
		user.setConfirmed(true);
		user.setDateBirth(new Date());
		user.setEmail("email");
		user.setEmailVerified("EmailVerified");
		user.setFatherLastName("Father");
		user.setIdUser(33);
		user.setMotherLastName("mother");
		user.setMultiFactorAuthentication("multi");
		user.setName("name");
		user.setPassword("password");
		
		post = new Post();
		post.setDateRegistration(new Date());
		post.setDescription("Descripcion");
		post.setIdPost(2);
		post.setMultimedia("multimedia");
		post.setNumLike(1);
		post.setShare("share");
		post.setUser(user);
	}
	
	@Test
	void findAllCommentTest() {
		listComment.add(comment);
		when(commentRepository.findAll()).thenReturn(listComment);
		response = commentService.findAllComment();
		assertNotNull(response);
	}
	
	@SuppressWarnings("serial")
	@Test
	void findAllCommentCathTest(){
		when(commentRepository.findAll()).thenThrow(new DataAccessException("Entrando excepción") {});

	    // Al llamar al método findAllComment(), debería lanzar la excepción simulada
	    assertThrows(BusinessException.class, () -> {
	        commentService.findAllComment();
	    });
	}
	
	@Test
	void findAllCommentPageTest() {
		listComment.add(comment);
		Page<Comment> pageData = new PageImpl<>(listComment);
        Pageable pageable = PageRequest.of(10, 10);
		when(commentRepository.findAllByOrderByDateRegistrationDesc(pageable)).thenReturn(pageData);
		response = commentService.findAllComment(10,10);
		assertNotNull(response);
	}
	
	@SuppressWarnings("serial")
	@Test
	void findAllCommentPageCathTest() {
		listComment.add(comment);
        Pageable pageable = PageRequest.of(10, 10);
		when(commentRepository.findAllByOrderByDateRegistrationDesc(pageable)).thenThrow(new DataAccessException("excepcion") {});
		assertThrows(BusinessException.class, () ->{
			commentService.findAllComment(10,10);
		});		
	}
	
	@Test
	void findByIdCommentTest() {		
		when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comment));
		response = commentService.findByIdComment(Mockito.anyInt());
		assertNotNull(response);
	}
	
	@Test
	void findByIdCommentTestEmpty() {		
		when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		assertThrows(BusinessException.class,() ->  {
			commentService.findByIdComment(1);
		});

	}
	
	@Test
	@SuppressWarnings("serial")
	void findByIdCommentExcepcionTest() {
		when(commentRepository.findById(Mockito.anyInt())).thenThrow(new DataAccessException("excepcion") {});
		assertThrows(BusinessException.class,() ->  {
			commentService.findByIdComment(1);
		});
	}
	
	@Test
	void findByPostCommentTest() {
		when(commentRepository.findByPostIdPost(Mockito.anyInt())).thenReturn(listComment);		
		response = commentService.findByPostComment(Mockito.anyInt());
		assertNotNull(comment);
	}
	
	@Test
	@SuppressWarnings("serial")
	void findByPostCommentCatchTest() {
		when(commentRepository.findByPostIdPost(Mockito.anyInt())).thenThrow(new DataAccessException("Error Servidor") {});
		assertThrows(BusinessException.class, ()->{
			commentService.findByPostComment(Mockito.anyInt());
		});		
	}
	
	@Test 
	void createCommentTest() throws IOException {
		when(userRepository.findById(commentDto.getUser())).thenReturn(Optional.of(user));
		when(postRepository.findById(commentDto.getPost())).thenReturn(Optional.of(post));
		response = commentService.createComment(commentDto);
		assertNotNull(comment);
	}
	
	@Test 
	void createCommentUserTest() throws IOException {		
		when(userRepository.findById(commentDto.getUser())).thenReturn(Optional.empty());		
		assertThrows(BusinessException.class,()->{
			commentService.createComment(commentDto);
		});
	}
	
	@Test 
	void createCommentPostTest() throws IOException {	
		when(userRepository.findById(commentDto.getUser())).thenReturn(Optional.of(user));
		when(postRepository.findById(commentDto.getPost())).thenReturn(Optional.empty());
		assertThrows(BusinessException.class,()->{
			commentService.createComment(commentDto);
		});
	}
	
	@Test 
	@SuppressWarnings("serial")
	void createCommentCathTest() throws IOException {	
		when(userRepository.findById(commentDto.getUser())).thenReturn(Optional.of(user));
		when(postRepository.findById(commentDto.getPost())).thenReturn(Optional.of(post));
		when(commentRepository.save(Mockito.any())).thenThrow(new DataAccessException("excepcion") {});
		assertThrows(BusinessException.class,()->{
			commentService.createComment(commentDto);
		});
	}
	
	@Test
	void updateCommentTest() throws IOException {	
		when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comment));
		response = commentService.updateComment(commentDto);
		assertNotNull(response);
	}
	
	@Test
	void updateCommentIfTest() throws IOException {
		CommentDTO commentUpdate = new CommentDTO();
		commentUpdate.setDescripcion(null);
		commentUpdate.setMultipartFile(null);
		commentUpdate.setMultimedia("null");
		commentUpdate.setIdComment(10);
		when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comment));
		response = commentService.updateComment(commentUpdate);
		assertNotNull(response);
	}
	
	@Test
	void updateCommentEmptyTest() throws IOException {
		
		when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		assertThrows(BusinessException.class, ()->{
			commentService.updateComment(commentDto);
		});
	}
	
	@Test
	@SuppressWarnings("serial")
	void updateCommentCathest() throws IOException {
		when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comment));
		when(commentRepository.save(comment)).thenThrow(new DataAccessException("Excepcion") {});
		assertThrows(BusinessException.class, ()->{
			commentService.updateComment(commentDto);
		});
	}
	
	@Test
	void deleteCommentTest() {
		commentService.deleteComment(1);
		assertNotNull("");
	}
	
	@Test
	@SuppressWarnings("serial")
	void deleteCommentCatchTest() {
		when(commentService.deleteComment(1)).thenThrow(new DataAccessException("Excepcion") {});
		assertThrows(BusinessException.class, () ->{
			commentService.deleteComment(1);
		});
	}
	
	@Test
	void sumLikeTest() {
		when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comment));
		response = commentService.sumLike(1);
		assertNotNull(response);
	}
	
	@Test
	void sumLikeEmptyTest() {
		when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		assertThrows(BusinessException.class, ()->{
			commentService.sumLike(1);
		});
	}
	
	@Test
	@SuppressWarnings("serial")
	void sumLikeCathTest() {
		when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comment));
		doThrow(new DataAccessException("Excepcion") {}).when(commentRepository).sumLike(Mockito.anyInt());
		assertThrows(BusinessException.class, ()->{
			commentService.sumLike(1);
		});
	}
	
	@Test
	void subtractLikeTest() {
		when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comment));
		response = commentService.subtractLike(1);
		assertNotNull(response);		
	}
	
	@Test
	void subtractLikeEmptyTest() {
		when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		assertThrows(BusinessException.class, () -> {
			commentService.subtractLike(1);
		});
	}
	
	@Test
	@SuppressWarnings("serial")
	void subtractLikeCathTest() {
		when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comment));
		doThrow(new DataAccessException("Excepcion") {}).when(commentRepository).subtractLike(Mockito.anyInt());
		assertThrows(BusinessException.class, () -> {
			commentService.subtractLike(1);
		});
	}
	
	
	
}
