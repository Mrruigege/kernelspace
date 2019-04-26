package com.rui.hyperlink.mapper;

import com.rui.hyperlink.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiaorui
 */
@Mapper
@Repository
public interface RoleMapper {

    List<Role> getRoleByUid(@Param("uid") Long userId);

    void insertRoleByUserId(@Param("uid") Long userId);
}
