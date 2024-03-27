package com.rishita.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TweetDto {

    private Long id;

    private String content;

    private String image;

    private String video;

    private UserDto user;

    private LocalDateTime createdAt;

    private int totalLikes;

    private int totalReplies;

    private int totalRetweets;

//   to know whichever user se logged in hai usne yeh post like kiya hai ya nhi
    private boolean isLiked;

//    same reason as above
    private boolean isRetweet;

//    ids of all the user's thht have retweeted
    private List<Long> retweetUserId;

    private List<TweetDto> replyTweets;




}
