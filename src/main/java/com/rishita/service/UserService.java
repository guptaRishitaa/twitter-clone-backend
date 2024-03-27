package com.rishita.service;

import com.rishita.exception.UserException;
import com.rishita.model.User;

import java.util.List;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

    public User updateUser(Long userId, User user) throws UserException;

//    for follow unfollow both
    public User followUser(Long userId, User user) throws UserException;

    public List<User> searchUser(String query);
}
