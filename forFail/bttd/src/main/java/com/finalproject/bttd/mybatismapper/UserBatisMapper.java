package com.finalproject.bttd.mybatismapper;

import com.finalproject.bttd.dto.UserDetailDto;
import com.finalproject.bttd.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserBatisMapper {

    UserDetailDto getUserDetails(String user_id);

}
