package com.socialhub.service;

import com.socialhub.model.dto.PostDTO;
import com.socialhub.model.entity.Post;
import com.socialhub.model.entity.Response;

public interface IPostService {

	public Response<Post> findAllPost();
	public Response<Post> findByIdPost(Integer idPost);
	public Response<Post> findByUserPost(Integer idUsuario);
	public Response<Post> createPost(PostDTO post);
	public Response<Post> updatePost(PostDTO post);
	public Response<Post> deletePost(Integer idPost);
	
	public Response<Post> sumLike(Integer idPost);
	public Response<Post> subtractLike(Integer idPost);
	
}
