package com.rui.hyperlink.mapper;

import com.rui.hyperlink.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author xiaorui
 */
@Repository
@Mapper
public interface UserMapper {

    User findUserByName(@Param("username") String username);
    Long insertUser(@Param("user") User user);
}
