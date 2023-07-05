package com.socialhub.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.socialhub.exceptions.BusinessException;
import com.socialhub.model.dto.PostDTO;
import com.socialhub.model.entity.Post;
import com.socialhub.model.entity.Response;
import com.socialhub.model.entity.User;
import com.socialhub.repository.IPostRepository;
import com.socialhub.repository.IUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostService implements IPostService{

	// Inyección de dependencia del repositorio de posts
	@Autowired
	private IPostRepository postRepository;

	// Inyección de dependencia del repositorio de usuarios
	@Autowired
	private IUserRepository userRepository;

	// Inyección de dependencia del servicio de estrategia de almacenamiento Firebase
	@Autowired
	private FirebaseStorageStrategyService firebaseStorageStrategyService;

	/**
	 * Buscar todos los posts que se encuentran en la base de datos.
	 */
	@Override
	public Response<Post> findAllPost() {
		
		// Se crea una instancia de la clase Response que se retornará
		Response<Post> response = new Response<Post>();
		
		// Se crea una lista vacía de posts
		List<Post> posts = new ArrayList<>();
		
		try {
			// Se obtienen todos los posts de la base de datos utilizando el postRepository
			posts = postRepository.findAll();
			
			// Se arman los datos del response con la información correcta
			response.setCount(posts.size());
			response.setList(posts);
			response.setStatus("OK");
			response.setMessage("Publicaciones obtenidas correctamente.");
			
		} catch (DataAccessException e) {
			// En caso de producirse una excepción de acceso a datos, se registra un error 
			//y se configuran los datos del response correspondientes
			log.error(e.getMessage());
			response.setList(null);
			response.setStatus("ERROR");
			response.setMessage("Ocurrió un error al consultar las publicaciones.");
			
			// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error al consultar las publicaciones.");
		}
		
		// Se retorna el response
		return response;
	}


	/**
	 * Busca una publicación por medio del ID.
	 */
	@Override
	public Response<Post> findByIdPost(Integer idPost) {
		// Se crea una instancia de la clase Response que se retornará.
		Response<Post> response = new Response<Post>();
		
		Post post = new Post();
		
		try {
			// Se busca la publicación por su ID utilizando el postRepository.
			Optional<Post> optional = postRepository.findById(idPost);
			
			if (optional.isPresent()) {
				// Si la publicación existe, se configuran los datos del response con la información correcta.
				post = optional.get();
				response.setData(post);
				response.setStatus("OK");
				response.setMessage("Publicación obtenida correctamente.");
			} else {
				// Si la publicación no existe, se configuran los datos del response indicando el error.
				response.setData(post);
				response.setStatus("ERROR");
				response.setMessage("No se encontró la publicación.");
				
				// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior.
				throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontró la publicación");
			}
		} catch (DataAccessException e) {
			// En caso de producirse una excepción de acceso a datos, se registra un error y se configuran los datos del response correspondientes.
			log.error(e.getMessage());
			response.setList(null);
			response.setStatus("ERROR");
			response.setMessage("Ocurrió un error al consultar la publicación.");
			
			// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior.
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error al consultar la publicación.");
		}
		
		// Se retorna el response.
		return response;
	}


	@Override
	public Response<Post> findByUserPost(Integer idUsuario) {
		// Se crea una instancia de la clase Response que se retornará.
		Response<Post> response = new Response<Post>();
		
		List<Post> posts = new ArrayList<>();
		
		try {
			// Se buscan las publicaciones del usuario utilizando el postRepository y el método findByUserIdUser().
			posts = postRepository.findByUserIdUser(idUsuario);
			
			// Se construye el mensaje dependiendo de si se encontraron publicaciones o no.
			String mensaje = (posts.size() > 0) ? "Publicaciones del usuario obtenidas correctamente." : "No hay publicaciones para este usuario.";
			
			// Se configuran los datos del response con la información correcta.
			response.setCount(posts.size());
			response.setList(posts);
			response.setStatus("OK");
			response.setMessage(mensaje);

		} catch (DataAccessException e) {
			// En caso de producirse una excepción de acceso a datos, se registra un error y se configuran los datos del response correspondientes.
			log.error(e.getMessage());
			response.setList(null);
			response.setStatus("ERROR");
			response.setMessage("Ocurrió un error al consultar las publicaciones del usuario.");
			
			// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior.
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error al consultar las publicaciones del usuario.");

		}
		
		// Se retorna el response.
		return response;
	}

	@Override
	public Response<Post> createPost(PostDTO post, Integer redSocial) throws IOException {
		
		// Se crea una instancia de la clase Response que se retornará.
		Response<Post> response = new Response<Post>();
		
		// Se busca el usuario por su ID utilizando el userRepository.
		Optional<User> optionalUser = userRepository.findById(post.getUser());
		User user = new User();
		
		if (optionalUser.isPresent()) {
			// Si el usuario existe, se obtiene el objeto User correspondiente.
			user = optionalUser.get();
			
			// Se crean las instancias necesarias para guardar la publicación.
			Post postSave = new Post();
			Post postSaveOk = new Post();
			String linkFirebase = "";
			
			if (post.getMultipartFile() != null) {
				// Si hay un archivo adjunto en el post, se utiliza el firebaseStorageStrategyService para subir el archivo a Firebase y se obtiene el enlace correspondiente.
				String[] url = firebaseStorageStrategyService.uploadFile(post.getMultipartFile(), redSocial);
				linkFirebase = url[0];
			}
			
			try {
				// Se configuran los datos de la publicación a guardar.
				postSave.setDescription(post.getDescription());
				postSave.setMultimedia(linkFirebase);
				postSave.setNumLike(post.getNumLike());
				postSave.setShare(post.getShare());
				postSave.setUser(user);
				
				// Se guarda la publicación utilizando el postRepository.
				postSaveOk = postRepository.save(postSave);
				
				// Se configuran los datos del response con la información correcta.
				response.setData(postSaveOk);
				response.setMessage("Publicación creada con éxito.");
				response.setStatus("OK");
				
			} catch (DataAccessException e) {
				// En caso de producirse una excepción de acceso a datos, se registra un error y se configuran los datos del response correspondientes.
				log.error(e.getMessage());
				response.setStatus("ERROR");
				response.setMessage("Error al crear la publicación.");
				
				// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior.
				throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la publicación.");
			}
			
		} else {
			// Si el usuario no existe, se configuran los datos del response indicando el error y se lanza una BusinessException.
			response.setStatus("ERROR");
			response.setMessage("El usuario no existe en la base de datos.");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El usuario no existe en la base de datos.");
		}
		
		// Se retorna el response.
		return response;
	}


	@Override
	public Response<Post> updatePost(PostDTO post, Integer redSocial) throws IOException {
		Response<Post> response = new Response<Post>();
		
		// Se busca la publicación por su ID utilizando el postRepository.
		Optional<Post> optionalPost = postRepository.findById(post.getIdPost());
		
		Post postFind = new Post();
		
		if (optionalPost.isPresent()) {
			// Si la publicación existe, se obtiene el objeto Post correspondiente.
			postFind = optionalPost.get();
			Post postSaveOk = new Post();
			
			String linkFirebase = "";
			
			if (post.getMultipartFile() != null) {
				// Si hay un archivo adjunto en el post, se utiliza el firebaseStorageStrategyService para subir el archivo a Firebase y se obtiene el enlace correspondiente.
				String[] url = firebaseStorageStrategyService.uploadFile(post.getMultipartFile(), redSocial);
				linkFirebase = url[0];
			}
			
			try {
				// Se actualizan los campos de la publicación solo si los valores no son nulos o vacíos.
				if (post.getDescription() != null && !post.getDescription().equals("")) {
					postFind.setDescription(post.getDescription());
				}
//				if(post.getNumLike()!=null||post.getNumLike()!=0) {
//					postFind.setNumLike(post.getNumLike());
//				}
				if (post.getMultipartFile() != null) {
					postFind.setMultimedia(linkFirebase);
				}
				if (post.getShare() != null && !post.getShare().equals("")) {
					postFind.setShare(post.getShare());
				}
//				if(post.getDescription()!=null||post.getDescription()!="") {
//					postSave.setUser(user);
//				}
//				if(post.getDescription()!=null||post.getDescription()!="") {
//					postFind.setIdPost(post.getIdPost());
//				}
				
				// Se guarda la publicación actualizada utilizando el postRepository.
				postSaveOk = postRepository.save(postFind);
				
				// Se configuran los datos del response con la información correcta.
				response.setData(postSaveOk);
				response.setMessage("Publicación actualizada con éxito.");
				response.setStatus("OK");
				
			} catch (DataAccessException e) {
				// En caso de producirse una excepción de acceso a datos, se registra un error y se configuran los datos del response correspondientes.
				log.error(e.getMessage());
				response.setStatus("ERROR");
				response.setMessage("Error al actualizar la publicación.");
				
				// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior.
				throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la publicación.");
			}
			
		} else {
			// Si la publicación no existe, se configuran los datos del response indicando el error y se lanza una BusinessException.
			response.setStatus("ERROR");
			response.setMessage("La publicación no existe en la base de datos.");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La publicación no existe en la base de datos.");
		}
		
		return response;
	}


	@Override
	public Response<Post> deletePost(Integer idPost) {
		
		// Se crea una instancia de la clase Response que se retornará.
		Response<Post> response = new Response<Post>();
		
		try {
			// Se elimina la publicación por su ID utilizando el postRepository.
			postRepository.deleteById(idPost);
			
			// Se configuran los datos del response con la información correcta.
			response.setMessage("Publicación eliminada con éxito.");
			response.setStatus("OK");
			
		} catch (DataAccessException e) {
			// En caso de producirse una excepción de acceso a datos, se registra un error y se configuran los datos del response correspondientes.
			log.error(e.getMessage());
			response.setMessage("Ocurrió un error al eliminar la publicación.");
			response.setStatus("ERROR");
			
			// Se lanza una BusinessException para indicar el error y manejarlo en un nivel superior.
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error al eliminar la publicación.");
		}
		
		return response;
	}

	@Override
	public Response<Post> sumLike(Integer idPost) {
		Response<Post> response = new Response<Post>();
		
		// Se busca la publicación por su ID utilizando el postRepository.
		Optional<Post> optional = postRepository.findById(idPost);
		
		if (optional.isPresent()) {
			try {
				// Se actualiza el número de likes utilizando el postRepository y el método sumLike().
				postRepository.sumLike(idPost);
				
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
	public Response<Post> subtractLike(Integer idPost) {
		Response<Post> response = new Response<Post>();
		
		// Se busca la publicación por su ID utilizando el postRepository.
		Optional<Post> optional = postRepository.findById(idPost);
		
		if (optional.isPresent()) {
			try {
				// Se resta el número de likes utilizando el postRepository y el método subtractLike().
				postRepository.subtractLike(idPost);
				
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
