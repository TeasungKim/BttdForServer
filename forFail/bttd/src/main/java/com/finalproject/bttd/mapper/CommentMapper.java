package com.finalproject.bttd.mapper;

import com.finalproject.bttd.dto.Boards;
import com.finalproject.bttd.dto.Comments;
import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.entity.Comment;

public class CommentMapper {

    public static Comments toDto(Comment comment) {
        Comments dto = new Comments();
        dto.setPost_id(comment.getPost_id().getPost_id());
        dto.setRequest_user_id(comment.getRequest_user_id());
        dto.setRequest_context(comment.getRequest_context());
        // 다른 필드들도 마찬가지로 설정
        return dto;
    }



}
