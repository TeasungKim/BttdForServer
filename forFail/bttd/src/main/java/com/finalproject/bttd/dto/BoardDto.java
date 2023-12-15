package com.finalproject.bttd.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.finalproject.bttd.Utils.UserDeserializer;
import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {


    private int post_id;

    private String post_title;
    private String post_context;
    private Date post_date;
    @JsonDeserialize(using = UserDeserializer.class)
    private User user_id;
    private String away_id;
    private boolean score;

//    public BoardDto() {
//        this.post_date = new Date();
//    }

    public Board toEntity() {
        return new Board(post_id, post_title, post_context, new Date(), user_id, away_id, score);
    }
}
