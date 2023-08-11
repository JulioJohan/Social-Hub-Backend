package com.socialhub.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	/**
	 * Maneja la solicitud para obtener todos las publicaciones existentes.
	 * 
	 * @return ResponseEntity con una lista de posts en el cuerpo de la respuesta.
	 */
	@GetMapping(path = "/findAllPost", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> findAllPost(){
	    // Invoca al servicio para obtener todos los posts
	    Response<Post> response = postService.findAllPost();
	    
	    // Retorna una respuesta con la lista de posts y el estado HTTP OK (200)
	    return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}
	/**
	 * Maneja la solicitud para obtener todos las publicaciones existentes por paginaciones	.
	 * 
	 * @return ResponseEntity con una lista de posts en el cuerpo de la respuesta.
	 */
	@GetMapping(path = "/findAllPost/{page}/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> findAllPost(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
		// Invoca al servicio para obtener todos los posts
		Response<Post> response = postService.findAllPost(1,page, size);
		
		// Retorna una respuesta con la lista de posts y el estado HTTP OK (200)
		return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}
	
	/**
	 * Maneja la solicitud para obtener todos las publicaciones existentes por paginaciones	de la red social toktik.
	 * 
	 * @return ResponseEntity con una lista de posts en el cuerpo de la respuesta.
	 */
	@GetMapping(path = "/findAllPostToktik/{page}/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> findAllPostToktik(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
		// Invoca al servicio para obtener todos los posts
		Response<Post> response = postService.findAllPost(2,page, size);
		
		// Retorna una respuesta con la lista de posts y el estado HTTP OK (200)
		return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}

	/**
	 * Maneja la solicitud para obtener un post específico por su ID.
	 * 
	 * @param idPost el ID del post a buscar.
	 * @return ResponseEntity con el post encontrado en el cuerpo de la respuesta.
	 */
	@GetMapping(path = "/findByIdPost/{idPost}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> findByIdPost(@PathVariable("idPost") Integer idPost){
	    // Invoca al servicio para buscar el post por su ID
	    Response<Post> response = postService.findByIdPost(idPost);
	    
	    // Retorna una respuesta con el post encontrado y el estado HTTP OK (200)
	    return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}

	/**
	 * Maneja la solicitud para obtener los posts de un usuario específico por su ID.
	 * 
	 * @param idUsuario el ID del usuario cuyos posts se desean buscar.
	 * @return ResponseEntity con una lista de posts del usuario en el cuerpo de la respuesta.
	 */
	@GetMapping(path = "/findByUserPost/{idUsuario}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> findByUserPost(@PathVariable("idUsuario") Integer idUsuario, @PathVariable("type") Integer type){
	    // Invoca al servicio para buscar los posts del usuario por su ID
	    Response<Post> response = postService.findByUserPost(type, idUsuario);
	    
	    // Retorna una respuesta con la lista de posts del usuario y el estado HTTP OK (200)
	    return new ResponseEntity<Response<Post>> (response, HttpStatus.OK);
	}

	
	/**
	 * Maneja la solicitud para crear un nuevo post.
	 *
	 * @param post los datos del post a crear.
	 * @return ResponseEntity con el post creado en el cuerpo de la respuesta.
	 * @throws IOException si ocurre algún error relacionado con la entrada/salida de datos.
	 */
	@PostMapping(path = "/createPost/{tipoPost}", produces = MediaType.APPLICATION_JSON_VALUE,
	            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Response<Post>> createPost(@ModelAttribute PostDTO post, @PathVariable("tipoPost") Integer tipoPost) throws IOException {
	    // Invoca al servicio para crear el post utilizando los datos proporcionados
		
		//Se envia el numero 0 para indicar que es una publicacion de bookface
		//Se envia el numero 1 para indicar que es una publicacion de toktic
		
		//Se ajusto el metodo para indicar los nuevos valores
		Integer tipo= null;
		if (tipoPost==0) {
			tipo = 1;
		}else {
			tipo = 2;
		}
	    Response<Post> response = postService.createPost(post, tipoPost, tipo);
	    
	    // Retorna una respuesta con el post creado y el estado HTTP OK (200)
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	/**
	 * Maneja la solicitud para actualizar un post existente.
	 *
	 * @param post los datos del post actualizado.
	 * @return ResponseEntity con el post actualizado en el cuerpo de la respuesta.
	 * @throws IOException si ocurre algún error relacionado con la entrada/salida de datos.
	 */
	@PutMapping(path = "/updatePost/{tipoPost}", produces = MediaType.APPLICATION_JSON_VALUE,
	            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Response<Post>> updatePost(@ModelAttribute PostDTO post, @PathVariable("tipoPost") Integer tipoPost) throws IOException {
	    // Invoca al servicio para actualizar el post utilizando los datos proporcionados
		//Se envia el numero 0 para indicar que es una publicacion de bookface
		//Se envia el numero 1 para indicar que es una publicacion de toktic
	    Response<Post> response = postService.updatePost(post, tipoPost);

	    // Retorna una respuesta con el post actualizado y el estado HTTP OK (200)
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	/**
	 * Maneja la solicitud para eliminar un post existente por su ID.
	 *
	 * @param idPost el ID del post a eliminar.
	 * @return ResponseEntity con información sobre el resultado de la eliminación en el cuerpo de la respuesta.
	 */
	@DeleteMapping(path = "/deletePost/{idPost}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> deletePost(@PathVariable("idPost") Integer idPost) {
	    // Invoca al servicio para eliminar el post por su ID
	    Response<Post> response = postService.deletePost(idPost);

	    // Retorna una respuesta con información sobre el resultado de la eliminación y el estado HTTP OK (200)
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	/**
	 * Maneja la solicitud para incrementar el contador de "likes" de un post por su ID.
	 *
	 * @param idPost el ID del post al cual se desea incrementar el contador de "likes".
	 * @return ResponseEntity con el post actualizado en el cuerpo de la respuesta.
	 */
	@PutMapping(path = "/sumLike/{idPost}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> sumLike(@PathVariable("idPost") Integer idPost) {
	    // Invoca al servicio para incrementar el contador de "likes" del post por su ID
	    Response<Post> response = postService.sumLike(idPost);

	    // Retorna una respuesta con el post actualizado y el estado HTTP OK (200)
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	/**
	 * Maneja la solicitud para restar el contador de "likes" de un post por su ID.
	 *
	 * @param idPost el ID del post al cual se desea decrementar el contador de "likes".
	 * @return ResponseEntity con el post actualizado en el cuerpo de la respuesta.
	 */
	@PutMapping(path = "/subtractLike/{idPost}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Post>> subtractLike(@PathVariable("idPost") Integer idPost) {
	    // Invoca al servicio para decrementar el contador de "likes" del post por su ID
	    Response<Post> response = postService.subtractLike(idPost);

	    // Retorna una respuesta con el post actualizado y el estado HTTP OK (200)
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
}
