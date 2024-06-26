package com.rishita.service;

import com.rishita.exception.TweetException;
import com.rishita.exception.UserException;
import com.rishita.model.Tweet;
import com.rishita.model.User;
import com.rishita.repository.TweetRepository;
import com.rishita.request.TweetReplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetServiceImplementation implements TweetService{

    @Autowired
    private TweetRepository tweetRepository;
    @Override
    public Tweet createTweet(Tweet req, User user) throws UserException {
        Tweet tweet = new Tweet();
        tweet.setContent(req.getContent());
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setImage(req.getImage());
        tweet.setUser(user);
        tweet.setReply(false);
        tweet.setTweet(true);
        tweet.setVideo(req.getVideo());

        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> findAllTweets() {

        return tweetRepository.findAllByIsTweetTrueOrderByCreatedAtDesc();
    }

    @Override
    public Tweet retweet(Long tweetId, User user) throws UserException, TweetException {
//       first finding tweet by using the tweet id
        Tweet tweet= findById(tweetId);
//        if user already present in the retweet list, then removing the user, otherwise adding it to the list
//        basically handling unretweet and retweet both
        if(tweet.getRetweetUser().contains(user)){
            tweet.getRetweetUser().remove(user);
        }
        else{
            tweet.getRetweetUser().add(user);
        }
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet findById(Long tweetId) throws TweetException {
        Tweet tweet= tweetRepository.findById(tweetId).orElseThrow(()->new TweetException("Tweet not found with id "+tweetId));
        return tweet;
    }

    @Override
    public void deleteTweetById(Long tweetId, Long userId) throws TweetException, UserException {

        Tweet tweet=findById(tweetId);
        if(!userId.equals(tweet.getUser().getId())){
            throw new UserException("You can't delete another user's tweet");
        }
        tweetRepository.deleteById(tweet.getId());
    }

    @Override
    public Tweet removeFromRetweet(Long tweetId, User user) throws TweetException, UserException {
        return null;
    }

    @Override
    public Tweet createReply(TweetReplyRequest req, User user) throws TweetException {

        //        Doing this to find konse tweet k liye yeh reply hai
        Tweet replyFor= findById(req.getTweetId());

        Tweet tweet = new Tweet();
        tweet.setContent(req.getContent());
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setImage(req.getImage());
        tweet.setUser(user);
        tweet.setReply(true);
        tweet.setTweet(false);
        tweet.setReplyFor(replyFor);

        Tweet savedReply= tweetRepository.save(tweet);

//        tweet.getReplyTweets().add(savedReply);
        replyFor.getReplyTweets().add(savedReply);
        tweetRepository.save(replyFor);

        return replyFor;
    }

    @Override
    public List<Tweet> getUserTweet(User user) {
        return tweetRepository.findByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(user, user.getId());
    }

    @Override
    public List<Tweet> findByLikesContainUser(User user) {

        return tweetRepository.findByLikesUser_id(user.getId());
    }
}
