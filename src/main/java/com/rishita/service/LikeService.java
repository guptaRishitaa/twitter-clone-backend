package com.rishita.service;

import com.rishita.exception.TweetException;
import com.rishita.exception.UserException;
import com.rishita.model.Like;
import com.rishita.model.User;

import java.util.List;

public interface LikeService {

//    implementing like and unlike with this method
    public Like likeTweet(Long tweetId, User user) throws UserException, TweetException;

    public List<Like> getAllLikes(Long tweetId) throws TweetException;


}
