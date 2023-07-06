package com.socialhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialhub.model.entity.Comment;

public interface ICommentRepository extends JpaRepository<Comment, Integer>{

}
