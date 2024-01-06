package com.finalproject.bttd.repository;

import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post_id = :board")
    Optional<Comment> findCommentByBoard(@Param("board") Board board);


    @Query("Select c From Comment c where c.post_id.post_id = :post_id")
    List<Comment> findAllByPostId(@Param("post_id") int post_id);






}
