package com.finalproject.bttd.dto;

import lombok.Data;

@Data
public class UserDetailDto {

    String user_id;
    String user_name;
    String user_age;
    String user_weight;
    int user_win;
    int user_lose;
    String photo;

}
