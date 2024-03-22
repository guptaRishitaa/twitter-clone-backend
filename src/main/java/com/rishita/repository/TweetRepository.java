package com.rishita.repository;

import com.rishita.model.Tweet;
import com.rishita.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

//   This finds all the tweets all the tweets that were created by the user (i.e., isTweet is true) and arranges them by the time they were created at.
    List<Tweet> findAllByIsTweetTrueOrderByCreatedAtDesc();

//    finding that user has retweeted how many tweets
    List<Tweet> findByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(User user, Long userId);

//   finds the tweets liked by the user
    List<Tweet> findByLikesContainingOrderByCreatedAtDesc(User user);

//   finds all the tweets of the user given the user id
    @Query("SELECT t FROM Tweet t JOIN t.likes l WHERE l.user.id=:userId")
    List<Tweet> findByLikesUser_id(Long userId);
}
