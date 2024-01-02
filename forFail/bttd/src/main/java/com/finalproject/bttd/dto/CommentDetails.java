package com.finalproject.bttd.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDetails {
    private Long request_form_id;
    private String request_user_id;
    private String request_context;
    private Date request_date;

}


