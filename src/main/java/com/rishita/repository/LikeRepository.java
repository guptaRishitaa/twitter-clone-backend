package com.rishita.repository;

import com.rishita.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

//    checking if like already exists on a post
    @Query("SELECT l FROM Like l WHERE l.user.id=:userId AND l.tweet.id=:tweetId")
    public Like isLikeExist(@Param("userId") Long userId, @Param("tweetId")Long tweetId);

    @Query("SELECT l FROM Like l WHERE l.tweet.id=:tweetId")
    public List<Like> findByTweetId(@Param("tweetId") Long tweetId);
}

