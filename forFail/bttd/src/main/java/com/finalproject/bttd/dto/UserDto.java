package com.finalproject.bttd.dto;


import com.finalproject.bttd.entity.Role;
import com.finalproject.bttd.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class UserDto {
    private Long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String user_id;
    private String user_name;
    private String user_age;
    private String user_weight;
    private String user_password;
    private List<Role> roles;
    private int user_win;
    private int user_lose;
    private String photo;

    private String verificationToken;
    private boolean enabled = false;

    public User toEntity(){
        return new User(id, user_id, user_name, user_age, user_weight, user_password, roles, user_win, user_lose, photo,verificationToken, enabled);
    }





}
