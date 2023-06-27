package com.socialhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialhub.model.entity.User;

public interface IUserRepository extends JpaRepository<User, Integer>{

}
