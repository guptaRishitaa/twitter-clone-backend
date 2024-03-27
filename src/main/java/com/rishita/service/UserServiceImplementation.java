package com.rishita.service;

import com.rishita.config.JwtProvider;
import com.rishita.exception.UserException;
import com.rishita.model.User;
import com.rishita.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserById(Long userId) throws UserException {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserException("user not found with id "+userId));

        return user;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
//        getting email from token
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UserException("user not found with email "+email);
        }
        return user;
    }

    @Override
    public User updateUser(Long userId, User req) throws UserException {
        User user = findUserById(userId);

        if(req.getFullName()!=null){
            user.setFullName(req.getFullName());
        }

        if(req.getImage()!=null){
            user.setImage(req.getImage());
        }

        if(req.getBackgroundImage()!=null){
            user.setBackgroundImage(req.getBackgroundImage());
        }

        if(req.getBirthDate()!=null){
            user.setBirthDate(req.getBirthDate());
        }

        if(req.getBio()!=null){
            user.setBio(req.getBio());
        }

        if(req.getLocation()!=null){
            user.setLocation(req.getLocation());
        }

        if(req.getWebsite()!=null){
            user.setWebsite(req.getWebsite());
        }


        return userRepository.save(user);
    }

    @Override
    public User followUser(Long userId, User user) throws UserException {
//       if the user wants to follow userToBeFollowed, toh user ki following mai userToBeFollowed yeh set hoga and userToBeFollowed iske followers mai user set hoga.
        User userToBeFollowed = findUserById(userId);

//        checking if the user to be followed is already present in that user's following list and also in the user to be followed's follower's list
        if(user.getFollowings().contains(userToBeFollowed) && userToBeFollowed.getFollowers().contains(user)){
//            implementing unfollow
            user.getFollowings().remove(userToBeFollowed);
            userToBeFollowed.getFollowers().remove(user);
        }
        else{
            user.getFollowings().add(userToBeFollowed);
            userToBeFollowed.getFollowers().add(user);
        }

        userRepository.save(userToBeFollowed);
        userRepository.save(user);
        return userToBeFollowed;
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }
}
