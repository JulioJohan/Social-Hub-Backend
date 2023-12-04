package com.socialhub.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialhub.model.entity.Response;
import com.socialhub.model.entity.User;
import com.socialhub.repository.IUserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioService implements IUsuarioService{
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	public Response<User> findByEmail(String correo) {
		// TODO Auto-generated method stub
		
		Response<User> response = new Response<>();
		
		Optional<User> optional= userRepository.findByEmail(correo);
		
		if(optional.isPresent()) {
			response.setData(optional.get());
			response.setMessage("Inicio de sesion correcto");
			response.setStatus("OK");
			log.info("Inicio sesion");
		}else {
			response.setMessage("No existe el correo.");
			response.setStatus("Error");
			log.info("NO existe el correo");
		}
		
		return response;
	}

	@Override
	public Response<User> createUser(User user) {
		// TODO Auto-generated method stub
		user.setPassword("");
		user.setConfirmed(true);
		user.setEmailVerified("");
		user.setMultiFactorAuthentication("");
		
		Response<User> response = new Response<>();
		user = userRepository.save(user);
		response.setData(user);
		response.setMessage("Usuario creado");
		response.setStatus("OK");
		
		return response;
	}
	
}
