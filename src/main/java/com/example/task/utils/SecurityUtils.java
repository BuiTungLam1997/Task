package com.example.task.utils;

import com.example.task.security.MyUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class SecurityUtils {
    public static MyUser getPrincipal(){
        return (MyUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
    public static List<String> getAuthorities() {
        List<String> result = new ArrayList<>();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) (SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        for (GrantedAuthority authority : authorities) {
            result.add(authority.getAuthority());
        }
        return result;
    }
}
