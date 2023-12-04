package com.socialhub.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialhub.model.entity.Response;
import com.socialhub.model.entity.User;
import com.socialhub.service.IUsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;
	
	/**
	 * Maneja la solicitud para obtener los posts de un usuario específico por su ID.
	 * 
	 * @param idUsuario el ID del usuario cuyos posts se desean buscar.
	 * @return ResponseEntity con una lista de posts del usuario en el cuerpo de la respuesta.
	 */
	@GetMapping(path = "/findUserByEmail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<User>> findByEmail(@PathVariable("email") String email){
	    // Invoca al servicio para buscar los posts del usuario por su ID
	    Response<User> response = usuarioService.findByEmail(email);
	    
	    // Retorna una respuesta con la lista de posts del usuario y el estado HTTP OK (200)
	    return new ResponseEntity<Response<User>> (response, HttpStatus.OK);
	}
	
	/*
    * Maneja la solicitud para crear un nuevo post.
    *
    * @param comment los datos del post a crear.
    * @return ResponseEntity con el post creado en el cuerpo de la respuesta.
    * @throws IOException si ocurre algún error relacionado con la entrada/salida de datos.
    */
   @PostMapping(path = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE,
           consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<Response<User>> createUser(@ModelAttribute User user) throws IOException {
       // Invoca al servicio para crear el post utilizando los datos proporcionados

       Response<User> response = usuarioService.createUser(user);

       // Retorna una respuesta con el post creado y el estado HTTP OK (200)
       return new ResponseEntity<>(response, HttpStatus.OK);
   }

	
}
