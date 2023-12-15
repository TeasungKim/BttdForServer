package com.finalproject.bttd.service;

import com.finalproject.bttd.dto.CommentDto;
import com.finalproject.bttd.entity.Comment;
import com.finalproject.bttd.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
   private CommentRepository commentRepository;

    public Comment comments (CommentDto commentDto){
        Comment comment = commentDto.toEntity();
        return commentRepository.save(comment);
    }



}
