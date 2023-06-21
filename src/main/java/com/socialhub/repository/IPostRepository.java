package com.socialhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.socialhub.model.entity.Post;

public interface IPostRepository extends JpaRepository<Post, Integer>{
	List<Post> findByUserIdUser(Integer idUser);
	
	@Transactional
    @Modifying
	@Query(value="UPDATE post "
			+ "SET num_like = num_like + 1 "
			+ "WHERE id_post = :idPost", nativeQuery=true)
    void sumLike(@Param("idPost") Integer idPost);
	@Transactional
	@Modifying
	@Query(value="UPDATE post "
			+ "SET num_like = CASE "
			+ "    WHEN num_like > 0 THEN num_like - 1 "
			+ "    ELSE num_like "
			+ "  END "
			+ "WHERE id_post = :idPost"
			+ "", nativeQuery=true)
	void subtractLike(@Param("idPost") Integer idPost);

}
