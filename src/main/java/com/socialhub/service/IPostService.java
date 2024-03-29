package com.socialhub.service;

import java.io.IOException;

import com.socialhub.model.dto.PostDTO;
import com.socialhub.model.entity.Post;
import com.socialhub.model.entity.Response;

public interface IPostService {

	public Response<Post> findAllPost();
	public Response<Post> findAllPost(int type,int page, int size);
	public Response<Post> findByIdPost(Integer idPost);
    Response<Post> findByUserPost(Integer type, Integer idUsuario);
    public Response<Post> createPost(PostDTO post, Integer redSocial, Integer type) throws IOException;
	public Response<Post> updatePost(PostDTO post, Integer redSocial) throws IOException;
	public Response<Post> deletePost(Integer idPost);
	
	public Response<Post> sumLike(Integer idPost);
	public Response<Post> subtractLike(Integer idPost);
	
}
