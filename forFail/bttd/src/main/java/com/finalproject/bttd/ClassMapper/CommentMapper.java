package com.finalproject.bttd.ClassMapper;

import com.finalproject.bttd.dto.CommentDetails;
import com.finalproject.bttd.dto.Comments;
import com.finalproject.bttd.dto.Posts;
import com.finalproject.bttd.entity.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static CommentDetails toDto(Comment comment) {
        CommentDetails details = new CommentDetails();
        details.setRequest_form_id(comment.getRequest_form_id());
        details.setRequest_user_id(comment.getRequest_user_id());
        details.setRequest_context(comment.getRequest_context());
        details.setRequest_date(comment.getRequest_date());

        // 다른 필드들도 마찬가지로 설정
        return details;
    }



}
