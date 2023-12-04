package com.socialhub.service;

import com.socialhub.model.entity.Response;
import com.socialhub.model.entity.User;

public interface IUsuarioService {
	public Response<User> findByEmail(String correo);
	
	public Response<User> createUser(User user);

}
