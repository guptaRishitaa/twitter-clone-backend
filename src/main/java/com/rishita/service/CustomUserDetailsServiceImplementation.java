package com.rishita.service;

import com.rishita.model.User;
import com.rishita.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsServiceImplementation implements UserDetailsService {

//we get UserDetailsService from Spring
    @Autowired
    private UserRepository userRepository;
//    checks if an user exists with this email in the db
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        finding user
        User user=userRepository.findByEmail(username);

//        if user logged in with google, then he will have to login with google always because when if logging with google, then we won't have password.
        if (user==null || user.isLogin_with_google()){
            throw new UsernameNotFoundException("Username not found with this email "+username);
        }

        List<GrantedAuthority> authorities=new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}
