package com.rishita.util;

import com.rishita.model.User;

public class UserUtils {

//    checks if the user is the requested user or not
    public static final boolean isReqUser(User reqUser, User user2){
        return reqUser.getId().equals(user2.getId());
    }

//    checks requested user folllows user 2 or not
    public static final boolean isFollowedByReqUser(User reqUser, User user2){

        return reqUser.getFollowings().contains(user2);
    }
}
