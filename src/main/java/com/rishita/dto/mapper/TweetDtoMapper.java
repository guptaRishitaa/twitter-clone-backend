package com.rishita.dto.mapper;

import com.rishita.dto.TweetDto;
import com.rishita.dto.UserDto;
import com.rishita.model.Tweet;
import com.rishita.model.User;
import com.rishita.util.TweetUtil;

import java.util.ArrayList;
import java.util.List;

public class TweetDtoMapper {

    public static TweetDto toTweetDto(Tweet tweet, User reqUser){

        UserDto user = UserDtoMapper.toUserDto(tweet.getUser());

//        that tweet is liked by the req user or not
        boolean isLiked = TweetUtil.isLikedByReqUser(reqUser, tweet);
        boolean isRetweeted = TweetUtil.isRetweetedByReqUser(reqUser, tweet);

        List<Long> retweetUserId = new ArrayList<>();
        for(User user1 : tweet.getRetweetUser()){
            retweetUserId.add(user1.getId());
        }

        TweetDto tweetDto = new TweetDto();

        tweetDto.setId(tweet.getId());
        tweetDto.setContent(tweet.getContent());
        tweetDto.setCreatedAt(tweet.getCreatedAt());
        tweetDto.setImage(tweet.getImage());
        tweetDto.setTotalLikes(tweet.getLikes().size());
        tweetDto.setTotalReplies(tweet.getReplyTweets().size());
        tweetDto.setTotalRetweets(tweet.getRetweetUser().size());
        tweetDto.setUser(user);
        tweetDto.setLiked(isLiked);
        tweetDto.setRetweet(isRetweeted);
        tweetDto.setRetweetUserId(retweetUserId);
        tweetDto.setReplyTweets(toTweetDtos(tweet.getReplyTweets(), reqUser));
        tweetDto.setVideo(tweet.getVideo());

        return tweetDto;

    }

    public static List<TweetDto> toTweetDtos(List<Tweet> tweets, User reqUser){
         List<TweetDto> tweetDtos = new ArrayList<>();

         for(Tweet tweet : tweets){
             TweetDto tweetDto = toReplyTweetDto(tweet, reqUser);
             tweetDtos.add(tweetDto);

         }
         return tweetDtos;
    }

    private static TweetDto toReplyTweetDto(Tweet tweet, User reqUser) {

        UserDto user = UserDtoMapper.toUserDto(tweet.getUser());

//        that tweet is liked by the req user or not
        boolean isLiked = TweetUtil.isLikedByReqUser(reqUser, tweet);
        boolean isRetweeted = TweetUtil.isRetweetedByReqUser(reqUser, tweet);

        List<Long> retweetUserId = new ArrayList<>();

        for(User user1 : tweet.getRetweetUser()){
            retweetUserId.add(user1.getId());
        }

        TweetDto tweetDto = new TweetDto();

        tweetDto.setId(tweet.getId());
        tweetDto.setContent(tweet.getContent());
        tweetDto.setCreatedAt(tweet.getCreatedAt());
        tweetDto.setImage(tweet.getImage());
        tweetDto.setTotalLikes(tweet.getLikes().size());
        tweetDto.setTotalReplies(tweet.getReplyTweets().size());
        tweetDto.setTotalRetweets(tweet.getRetweetUser().size());
        tweetDto.setUser(user);
        tweetDto.setLiked(isLiked);
        tweetDto.setRetweet(isRetweeted);
        tweetDto.setRetweetUserId(retweetUserId);
        tweetDto.setVideo(tweet.getVideo());

        return tweetDto;

    }
}
