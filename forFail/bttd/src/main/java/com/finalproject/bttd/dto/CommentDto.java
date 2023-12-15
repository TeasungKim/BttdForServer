package com.finalproject.bttd.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.finalproject.bttd.Utils.BoardDeserializer;
import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.entity.Comment;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {

    private Long request_form_id;
    private String request_user_id;
    private String request_context;
    private Date request_date;
    @JsonProperty("post_id")
    @JsonDeserialize(using = BoardDeserializer.class)
    private Board post_id;

    public Comment toEntity() {
        return new Comment(request_form_id, request_user_id, request_context, new Date(), post_id);
    }
}
