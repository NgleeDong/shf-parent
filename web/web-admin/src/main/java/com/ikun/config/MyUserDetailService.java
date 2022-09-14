package com.ikun.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ikun.entity.Admin;
import com.ikun.service.AdminService;
import com.ikun.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class MyUserDetailService implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminService.geAdminByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户不存在!");
        }
        //给用户授权 ===============
        //获取当前用户权限码
        List<String> permissionCodeList = permissionService.getPermissionCodeByAdminId(admin.getId());
        //创建一个用于授权的集合
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        //遍历得到每一个权限码
        for (String permissionCode : permissionCodeList) {
            //GrantedAuthority是一个接口，SimpleGrantedAuthority是它的一个实现类
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionCode);
            grantedAuthorities.add(simpleGrantedAuthority);
        }
//        return new User(username, admin.getPassword(),
//                AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        return new User(username, admin.getPassword(), grantedAuthorities);
    }
}
