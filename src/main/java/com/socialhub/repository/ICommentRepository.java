package com.socialhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialhub.model.entity.Comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment, Integer>{

    List<Comment> findByPostIdPost(Integer idComment);


    @Transactional
    @Modifying
    @Query(value="UPDATE comment "
            + "SET num_like = num_like + 1 "
            + "WHERE id_comment = :idComment", nativeQuery=true)
    void sumLike(@Param("idComment") Integer idComment);
    @Transactional
    @Modifying
    @Query(value="UPDATE comment "
            + "SET num_like = CASE "
            + "    WHEN num_like > 0 THEN num_like - 1 "
            + "    ELSE num_like "
            + "  END "
            + "WHERE id_comment = :idComment"
            + "", nativeQuery=true)
    void subtractLike(@Param("idComment") Integer idComment);


}
