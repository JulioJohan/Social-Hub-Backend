package com.socialhub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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

	@Autowired
	private IPostRepository postRepository;
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	public Response<Post> findAllPost() {
		
		//Response que se regresara.
		Response<Post> response= new Response<Post>();
		
		List<Post> posts= new ArrayList<>();
		
		try {
			
			posts= postRepository.findAll();
			//Armando el response con los datos correctos.
			response.setCount(posts.size());
			response.setList(posts);
			response.setStatus("OK");
			response.setMessage("Publicaciones obtenidas correctamente.");
			
		} catch (DataAccessException e) {
			log.error(e.getMessage());
			response.setList(null);
			response.setStatus("ERROR");
			response.setMessage("Ocurrio un error el consultar las publicaciones.");
			return response;
		}
		
		return response;
	}

	@Override
	public Response<Post> findByIdPost(Integer idPost) {
		//Response que se regresara.
		Response<Post> response= new Response<Post>();
		
		Post post= new Post(); 
		
		try {
			Optional<Post> optional=postRepository.findById(idPost);
			
			if(optional.isPresent()) {
				//Armando el response con los datos correctos.
				post= optional.get();
				response.setData(post);
				response.setStatus("OK");
				response.setMessage("Publicacion obtenidas correctamente.");
			}else {
				response.setData(post);
				response.setStatus("ERROR");
				response.setMessage("No se encontro la publicación");
			}
		} catch (DataAccessException e) {
			log.error(e.getMessage());
			response.setList(null);
			response.setStatus("ERROR");
			response.setMessage("Ocurrio un error el consultar la publicacion.");
			return response;
		}
		
		return response;
	}

	@Override
	public Response<Post> findByUserPost(Integer idUsuario) {
		//Response que se regresara.
		Response<Post> response= new Response<Post>();
		
		List<Post> posts= new ArrayList<>();
		
		try {
			
			posts= postRepository.findByUserIdUser(idUsuario);
			
			String mensaje=(posts.size()>0)?"Publicaciones del usuario obtenidas correctamente.":"No hay publicaciones para este usuario.";
			//Armando el response con los datos correctos.
			response.setCount(posts.size());
			response.setList(posts);
			response.setStatus("OK");
			response.setMessage(mensaje);
			
		} catch (DataAccessException e) {
			log.error(e.getMessage());
			response.setList(null);
			response.setStatus("ERROR");
			response.setMessage("Ocurrio un error el consultar las publicaciones del usuario.");
			return response;
		}
		
		return response;
	}

	@Override
	public Response<Post> createPost(PostDTO post) {
		
		Response<Post> response= new Response<Post>();
		
		Optional<User> optionalUser =userRepository.findById(post.getUser());
		User user = new User();
		if(optionalUser.isPresent()) {
			user= optionalUser.get();
			Post postSave= new Post();
			Post postSaveOk= new Post();
			try {
				postSave.setDescription(post.getDescription());
				postSave.setMultimedia(post.getMultimedia());
				postSave.setNumLike(post.getNumLike());
				postSave.setShare(post.getShare());
				postSave.setUser(user);
				
				postSaveOk= postRepository.save(postSave);
				
				response.setData(postSaveOk);
				response.setMessage("Publicación creada con exito");
				response.setStatus("OK");
				
			} catch (DataAccessException e) {
				log.error(e.getMessage());
				// TODO: handle exception
				response.setStatus("ERROR");
				response.setMessage("Error al crear la publicación");
				return response;
			}
			
		}else {
			response.setStatus("ERROR");
			response.setMessage("El usuario no existe en la base de datos.");
			return response;
		}
		
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public Response<Post> updatePost(PostDTO post) {
		Response<Post> response= new Response<Post>();
		
		Optional<Post> optionalPost =postRepository.findById(post.getIdPost());
		
		
		Post postFind = new Post();
		
		
		if(optionalPost.isPresent()) {
			postFind= optionalPost.get();
//			Post postSave= new Post();
			Post postSaveOk= new Post();
			try {
				
				if(post.getDescription()!=null||!"".equals(post.getDescription())) {
					postFind.setDescription(post.getDescription());
				}
				if(post.getMultimedia()!=null||"".equals(post.getMultimedia())) {
					postFind.setMultimedia(post.getMultimedia());
				}
//				if(post.getNumLike()!=null||post.getNumLike()!=0) {
//					postFind.setNumLike(post.getNumLike());
//				}
				if(post.getShare()!=null||!"".equals(post.getShare())) {
					postFind.setShare(post.getShare());
				}
//				if(post.getDescription()!=null||post.getDescription()!="") {
//					postSave.setUser(user);
//				}
//				if(post.getDescription()!=null||post.getDescription()!="") {
//					postFind.setIdPost(post.getIdPost());
//				}
				
				postSaveOk= postRepository.save(postFind);
				
				response.setData(postSaveOk);
				response.setMessage("Publicación actualizada con exito");
				response.setStatus("OK");
				
			} catch (DataAccessException e) {
				log.error(e.getMessage());
				// TODO: handle exception
				response.setStatus("ERROR");
				response.setMessage("Error al actualizar la publicación");
				return response;
			}
			
		}else {
			response.setStatus("ERROR");
			response.setMessage("El usuario no existe en la base de datos.");
			return response;
		}
		
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public Response<Post> deletePost(Integer idPost) {
		
		Response<Post> response= new Response<Post>();
		
		try {
			postRepository.deleteById(idPost);
			response.setMessage("Publicacion eliminada con éxito");
			response.setStatus("OK");
		} catch (DataAccessException e) {
			log.error(e.getMessage());
			response.setMessage("Ocurrio un error al elimnar la publicación");
			response.setStatus("ERROR");
			return response;
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public Response<Post> sumLike(Integer idPost) {
		Response<Post> response = new Response<Post>();
		Optional<Post> optional= postRepository.findById(idPost);
		
		if(optional.isPresent()) {
			
			try {
				postRepository.sumLike(idPost);
				response.setMessage("Like actualizado correctamente");
				response.setData(optional.get());
				response.setStatus("OK");
			} catch (DataAccessException e) {
				log.error(e.getMessage());
				response.setMessage("Ocurrio unn error el actualizar el número de likes");
				response.setStatus("ERROR");
				return response;
				// TODO: handle exception
			}
			
		}else {
			response.setMessage("La publicaciÓn no existe en la base de datos.");
			response.setStatus("ERROR");
		}
		
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public Response<Post> subtractLike(Integer idPost) {
		Response<Post> response = new Response<Post>();
		Optional<Post> optional= postRepository.findById(idPost);
		
		if(optional.isPresent()) {
			
			try {
				postRepository.subtractLike(idPost);
				response.setMessage("Like actualizado correctamente");
				response.setData(optional.get());
				response.setStatus("OK");
			} catch (DataAccessException e) {
				log.error(e.getMessage());
				response.setMessage("Ocurrio unn error el actualizar el número de likes");
				response.setStatus("ERROR");
				return response;
				// TODO: handle exception
			}
			
		}else {
			response.setMessage("La publicaciÓn no existe en la base de datos.");
			response.setStatus("ERROR");
		}
		
		// TODO Auto-generated method stub
		return response;

	}
	
	
	
	
}
