package com.socialhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialhub.model.entity.User;
import java.util.List;
import java.util.Optional;


public interface IUserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByEmail(String email);
}
