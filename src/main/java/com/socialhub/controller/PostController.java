package com.socialhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialhub.model.dto.PostDTO;
import com.socialhub.model.entity.Post;
import com.socialhub.model.entity.Response;
import com.socialhub.service.IPostService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private IPostService postService;
	
	@GetMapping(path = "/findAllPost", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> findAllPost(){
		
		Response<Post> response= postService.findAllPost();
		
		return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/findByIdPost/{idPost}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> findByIdPost(@PathVariable("idPost") Integer idPost){
		
		Response<Post> response= postService.findByIdPost(idPost);
		
		return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/findByUserPost/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> findByUserPost(@PathVariable("idUsuario") Integer idUsuario){
		
		Response<Post> response= postService.findByUserPost(idUsuario);
		
		return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}
	
	@PostMapping(path = "/createPost", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> createPost(@RequestBody PostDTO post){
		Response<Post> response= postService.createPost(post);
		
		return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}
	
	@PutMapping (path = "/updatePost", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> updatePost(@RequestBody PostDTO post){
		Response<Post> response= postService.updatePost(post);
		return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}
	
	@DeleteMapping (path = "/deletePost/{idPost}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> deletePost(@PathVariable("idPost") Integer idPost){
		Response<Post> response= postService.deletePost(idPost);
		return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}
	
	@PutMapping(path = "/sumLike/{idPost}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> sumLike(@PathVariable("idPost") Integer idPost){
		
		Response<Post> response= postService.sumLike(idPost);
		
		return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}
	
	
	@PutMapping(path = "/subtractLike/{idPost}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> subtractLike(@PathVariable("idPost") Integer idPost){
		
		Response<Post> response= postService.subtractLike(idPost);
		
		return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}
	
}
