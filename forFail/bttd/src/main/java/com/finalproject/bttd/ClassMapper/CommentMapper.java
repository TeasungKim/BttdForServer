package com.finalproject.bttd.ClassMapper;

import com.finalproject.bttd.dto.Comments;
import com.finalproject.bttd.entity.Comment;

import java.util.List;

public class CommentMapper {

    public static Comments toDto(Comment comment) {
        Comments dto = new Comments();
        dto.setPost_id(comment.getPost_id().getPost_id());
        dto.setPost_title(comment.getPost_id().getPost_title());
        dto.setPost_context(comment.getPost_id().getPost_context());
        dto.setRequest_form_id(comment.getRequest_form_id());
        dto.setRequest_context(comment.getRequest_context());
        dto.setRequest_date(comment.getRequest_date());
        // 다른 필드들도 마찬가지로 설정
        return dto;
    }



}
