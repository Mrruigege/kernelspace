package com.rui.hyperlink.security;

import com.rui.hyperlink.entity.Role;
import com.rui.hyperlink.entity.User;
import com.rui.hyperlink.mapper.RoleMapper;
import com.rui.hyperlink.mapper.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaorui
 */
@Service
public class SelfUserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SelfUserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        if (name == null) {
            return null;
        }

        User user = userMapper.findUserByName(name);
        if (user == null) {
            return null;
        }

        SelfUserDetails selfUserDetail = modelMapper.map(user, SelfUserDetails.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = roleMapper.getRoleByUid(user.getId());

        if (roles == null) {
            throw  new RuntimeException("权限错误");
        }
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        selfUserDetail.setAuthorityList(authorities);
        return selfUserDetail;
    }
}
