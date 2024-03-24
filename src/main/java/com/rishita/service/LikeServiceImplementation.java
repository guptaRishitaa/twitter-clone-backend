package com.rishita.service;

import com.rishita.exception.TweetException;
import com.rishita.exception.UserException;
import com.rishita.model.Like;
import com.rishita.model.Tweet;
import com.rishita.model.User;
import com.rishita.repository.LikeRepository;
import com.rishita.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImplementation implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TweetService tweetService;

    @Override
    public Like likeTweet(Long tweetId, User user) throws UserException, TweetException {
//        checking if like already exists
            Like isLikeExist = likeRepository.isLikeExist(user.getId(), tweetId);

            if(isLikeExist!=null){

//               means unlike the post
                likeRepository.deleteById(isLikeExist.getId());
                return isLikeExist;
            }

        Tweet tweet = tweetService.findById(tweetId);

            Like like = new Like();
            like.setTweet(tweet);
            like.setUser(user);

            Like savedLike = likeRepository.save(like);

            tweet.getLikes().add(savedLike);
            tweetRepository.save(tweet);
        return savedLike;
    }

    @Override
    public List<Like> getAllLikes(Long tweetId) throws TweetException {
        Tweet tweet = tweetService.findById(tweetId);
        List<Like> likes = likeRepository.findByTweetId(tweetId);
        return likes;
    }
}
