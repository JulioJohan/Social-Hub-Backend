package com.socialhub.service;

import com.socialhub.model.dto.CommentDTO;
import com.socialhub.model.entity.Comment;
import com.socialhub.model.entity.Response;

import java.io.IOException;

public interface ICommentService {
	
	public Response<Comment> findAllComment();
	public Response<Comment> findAllComment(int page, int size);
	public Response<Comment> findByIdComment(Integer idComment);
	public Response<Comment> findByPostComment(Integer idUsuario);
	public Response<Comment> createComment(CommentDTO comment) throws IOException;
	public Response<Comment> updateComment(CommentDTO post) throws IOException;
	public Response<Comment> deleteComment(Integer idPost);
	public Response<Comment> sumLike(Integer idPost);
	public Response<Comment> subtractLike(Integer idPost);
}
