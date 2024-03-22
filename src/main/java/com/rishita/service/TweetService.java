package com.rishita.service;

import com.rishita.exception.TweetException;
import com.rishita.exception.UserException;
import com.rishita.model.Tweet;
import com.rishita.model.User;
import com.rishita.request.TweetReplyRequest;

import java.util.List;

public interface TweetService {

    public Tweet createTweet(Tweet req, User user) throws UserException;

    public List<Tweet> findAllTweets();

    public Tweet retweet(Long tweetId, User user) throws UserException, TweetException;

    public Tweet findById(Long tweetId) throws TweetException;

    public void deleteTweetById(Long tweetId, Long userId) throws TweetException, UserException;

    public Tweet removeFromRetweet(Long tweetId, User user) throws TweetException, UserException;

    public Tweet createReply(TweetReplyRequest req, User user) throws TweetException;

    public List<Tweet> getUserTweet(User user);

//    Tweets liked by the user
    public List<Tweet> findByLikesContainUser(User user);

}
