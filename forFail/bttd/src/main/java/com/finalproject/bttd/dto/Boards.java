package com.finalproject.bttd.dto;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class Boards {
    public int post_id;
    public String post_context;
    public String post_title;
    public String away_id;
    public Boolean score;
    public int comment_count;



}
