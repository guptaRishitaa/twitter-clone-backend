package com.rishita.util;

import com.rishita.model.Like;
import com.rishita.model.Tweet;
import com.rishita.model.User;

public class TweetUtil {

    public final static boolean isLikedByReqUser(User reqUser, Tweet tweet){
        for(Like like : tweet.getLikes()){
//            condition true means req user liked the tweet
            if(like.getUser().getId().equals(reqUser.getId())){
                return true;
            }
        }
        return false;
    }

    public final static boolean isRetweetedByReqUser(User reqUser, Tweet tweet){
        for(User user : tweet.getRetweetUser()){
            if(user.getId().equals(reqUser.getId())){
                return true;
            }
        }

        return false;
    }
}
