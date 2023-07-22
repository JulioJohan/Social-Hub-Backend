package com.socialhub.service;

import com.socialhub.exceptions.BusinessException;
import com.socialhub.model.entity.Post;
import com.socialhub.model.entity.User;
import com.socialhub.repository.IPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.socialhub.model.dto.CommentDTO;
import com.socialhub.model.entity.Comment;
import com.socialhub.model.entity.Response;
import com.socialhub.repository.ICommentRepository;
import com.socialhub.repository.IUserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentService implements ICommentService{

	// Inyeccion de dependendencias del repositorio de comentarios
	@Autowired
	private ICommentRepository commentRepository;

	// Inyección de dependencia del repositorio de usuarios
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IPostRepository postRepository;

	// Inyección de dependencia del servicio de estrategia de almacenamiento Firebase
	@Autowired
	private FirebaseStorageStrategyService firebaseStorageStrategyService;

	@Override
	public Response<Comment> findAllComment() {
		// Se crea una instancia de la clase Response que se retornará
		Response<Comment> response = new Response<Comment>();

		//Se crea lista vacia de post.
		List<Comment> comments= new ArrayList<>();

		try {
			comments = commentRepository.findAll();
			response.setCount(comments.size());
			response.setList(comments);
			response.setStatus("OK");
			response.setMessage("Comentarios obteneidos con exito.");
		}catch (DataAccessException e){
			// En caso de producirse una excepción de acceso a datos, se registra un error
			log.error(e.getMessage());
			// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio unn error al consultar las publicaciones");
		}

		return response;
	}
	
	@Override
	public Response<Comment> findAllComment(int page, int size) {
		// Se crea una instancia de la clase Response que se retornará
		Response<Comment> response = new Response<Comment>();

		//Se crea lista vacia de post.
		List<Comment> comments= new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);


		try {
//			comments = commentRepository.findAll();
			Page<Comment> pageData = commentRepository.findAllByOrderByDateRegistrationDesc(pageable);
			long total= pageData.getTotalElements();

			response.setCount((int) total);
			response.setList(pageData.getContent());
			response.setStatus("OK");
			response.setMessage("Comentarios obteneidos con exito.");
		}catch (DataAccessException e){
			// En caso de producirse una excepción de acceso a datos, se registra un error
			log.error(e.getMessage());
			// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio unn error al consultar las publicaciones");
		}

		return response;
	}

	@Override
	public Response<Comment> findByIdComment(Integer idComment) {
		Response <Comment> response= new Response<Comment>();

		Comment comment = new Comment();

		try {
			Optional<Comment> optinonal= commentRepository.findById(idComment);

			if (optinonal.isPresent()){
				comment=optinonal.get();
				response.setData(comment);
				response.setStatus("OK");
				response.setMessage("Comentarios obtenidos correctamente.");
			}else{
				throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontró el comentario.");
			}

		}catch (DataAccessException e){
			// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior.
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error al consultar el comentario");
		}
		return response;
	}

	@Override
	public Response<Comment> findByPostComment(Integer idPost) {
		Response <Comment> response= new Response<Comment>();
		List<Comment> comments= new ArrayList<>();

		try {
			comments= commentRepository.findByPostIdPost(idPost);

			// Se construye el mensaje dependiendo de si se encontraron publicaciones o no.
			String mensaje = (comments.size() > 0) ? "Comentarios del usuario obtenidas correctamente." : "No hay comentarios para este usuario.";

			// Se configuran los datos del response con la información correcta.
			response.setCount(comments.size());
			response.setList(comments);
			response.setStatus("OK");
			response.setMessage(mensaje);

		}catch (DataAccessException e){
			// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior.
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error al consultar los comentarios del usuario.");
		}


		return response;
	}

	@Override
	public Response<Comment> createComment(CommentDTO comment) throws IOException {
		Response <Comment> response= new Response<Comment>();
		//Comentario al que le setean los parametros
		Comment comment1ToSave = new Comment();
		//Comenatario que se guardo.
		Comment comment1ToSaveOk= new Comment();

		//Variable para guardar el link archivo en firebase.
		String linkFirebase = "";
		//Se busca el usuario por su ID.
		Optional<User> optionalUser= userRepository.findById(comment.getUser());
		//User user = new User();
		if (optionalUser.isPresent()){
			comment1ToSave.setUser(optionalUser.get());
		}else{
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El usuario no existe en la base de datos.");
		}

		//Optinal para la publicacion.
		Optional<Post> optionalPost= postRepository.findById(comment.getPost());
		//Post post= new Post();
		if (optionalPost.isPresent() ){
			comment1ToSave.setPost(optionalPost.get());
		}else{
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La publicación no existe en la base de datos.");
		}

		//ASigando multimedia
		if (comment.getMultipartFile() != null) {
			// Si hay un archivo adjunto en el post, se utiliza el firebaseStorageStrategyService para subir el archivo a Firebase y se obtiene el enlace correspondiente.
			String[] url = firebaseStorageStrategyService.uploadFile(comment.getMultipartFile(), 2);
			linkFirebase = url[0];
		}

		try {
			comment1ToSave.setDescripcion(comment.getDescripcion());
			comment1ToSave.setMultimedia(linkFirebase);
			comment1ToSave.setNumLike(0);

			//Se guarda el comentario
			comment1ToSaveOk= commentRepository.save(comment1ToSave);
			// Se configuran los datos del response con la información correcta.
			response.setData(comment1ToSaveOk);
			response.setMessage("Comentario creado con éxito.");
			response.setStatus("OK");

		}catch (DataAccessException e){
			log.error(e.getMessage());
			// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior.
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear el comentario.");

		}

		return response;
	}

	@Override
	public Response<Comment> updateComment(CommentDTO comment) throws IOException {
		Response <Comment> response= new Response<Comment>();

		//Se busca el comentario por su id para verificar que existe.
		Optional<Comment> optionalComment= commentRepository.findById(comment.getIdComment());

		//Comentario al que le setean los parametros
		Comment commentFind = new Comment();
		//Comenatario que se guardo.
		Comment comment1Update= new Comment();

		//Variable para guardar el link archivo en firebase.
		String linkFirebase = "";

		try {

			if (optionalComment.isPresent()){
				commentFind= optionalComment.get();

				if(comment.getDescripcion()!=null || comment.getDescripcion().equals("")){
					commentFind.setDescripcion(comment.getDescripcion());
				}
				//ASigando multimedia
				if (comment.getMultipartFile() != null) {
					// Si hay un archivo adjunto en el post, se utiliza el firebaseStorageStrategyService para subir el archivo a Firebase y se obtiene el enlace correspondiente.
					String[] url = firebaseStorageStrategyService.uploadFile(comment.getMultipartFile(), 2);
					linkFirebase = url[0];
					commentFind.setMultimedia(linkFirebase);
				}
				
				log.info( "getMultimedia"+comment.getMultimedia());

				if(comment.getMultimedia().equals(null) || comment.getMultimedia().equals("null")) {
					log.info("Entre condicion");
					commentFind.setMultimedia(null);
				}
				log.info( "commentFind"+commentFind.getMultimedia());

				//Se guarda el comentario
				comment1Update= commentRepository.save(commentFind);
				log.info( "getMultimedia"+comment1Update.getMultimedia());

				// Se configuran los datos del response con la información correcta.
				response.setData(comment1Update);
				response.setMessage("Comentario actualizado con éxito.");
				response.setStatus("OK");

			}else{
				throw new BusinessException(HttpStatus.BAD_REQUEST, "El comentario no existe en la base de datos");
			}
		}catch (DataAccessException e){
			log.error(e.getMessage());
			// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior.
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el comentario.");

		}

		return response;
	}

	@Override
	public Response<Comment> deleteComment(Integer idPost) {
		Response <Comment> response= new Response<Comment>();

		try {
			commentRepository.deleteById(idPost);
			response.setMessage("Comentario eliminado con exitó");
			response.setStatus("OK");
		}catch (DataAccessException e){
			log.error(e.getMessage());
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Comentario eliminado con exito.");
		}
		return response;
	}

	@Override
	public Response<Comment> sumLike(Integer idComment) {
		Response <Comment> response= new Response<Comment>();

		// Se busca la publicación por su ID utilizando el postRepository.
		Optional<Comment> optional = commentRepository.findById(idComment);

		if (optional.isPresent()) {
			try {
				// Se actualiza el número de likes utilizando el postRepository y el método sumLike().
				commentRepository.sumLike(idComment);
				optional = commentRepository.findById(idComment);
				// Se configuran los datos del response con la información correcta.
				response.setMessage("Like actualizado correctamente.");
				response.setData(optional.get());
				response.setStatus("OK");
			} catch (DataAccessException e) {
				// En caso de producirse una excepción de acceso a datos, se registra un error y se configuran los datos del response correspondientes.
				log.error(e.getMessage());
				response.setMessage("Ocurrió un error al actualizar el número de likes.");
				response.setStatus("ERROR");

				// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior.
				throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error al actualizar el número de likes.");
			}

		} else {
			// Si la publicación no existe, se configuran los datos del response indicando el error y se lanza una BusinessException.
			response.setMessage("La publicación no existe en la base de datos.");
			response.setStatus("ERROR");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La publicación no existe en la base de datos.");
		}

		return response;
	}

	@Override
	public Response<Comment> subtractLike(Integer idComment) {

		Response <Comment> response= new Response<Comment>();

		// Se busca la publicación por su ID utilizando el postRepository.
		Optional<Comment> optional = commentRepository.findById(idComment);

		if (optional.isPresent()) {
			try {
				// Se resta el número de likes utilizando el postRepository y el método subtractLike().
				commentRepository.subtractLike(idComment);
				optional = commentRepository.findById(idComment);
				// Se configuran los datos del response con la información correcta.
				response.setMessage("Like actualizado correctamente.");
				response.setData(optional.get());
				response.setStatus("OK");
			} catch (DataAccessException e) {
				// En caso de producirse una excepción de acceso a datos, se registra un error y se configuran los datos del response correspondientes.
				log.error(e.getMessage());
				response.setMessage("Ocurrió un error al actualizar el número de likes.");
				response.setStatus("ERROR");

				// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior.
				throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error al actualizar el número de likes.");
			}

		} else {
			// Si la publicación no existe, se configuran los datos del response indicando el error y se lanza una BusinessException.
			response.setMessage("La publicación no existe en la base de datos.");
			response.setStatus("ERROR");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La publicación no existe en la base de datos.");
		}

		return response;
	}

} 
