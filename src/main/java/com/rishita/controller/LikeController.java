package com.rishita.controller;

import com.rishita.dto.LikeDto;
import com.rishita.dto.mapper.LikeDtoMapper;
import com.rishita.exception.TweetException;
import com.rishita.exception.UserException;
import com.rishita.model.Like;
import com.rishita.model.User;
import com.rishita.service.LikeService;
import com.rishita.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LikeController {

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;


    @PostMapping("/{tweetId}/likeTweet")
    public ResponseEntity<LikeDto> likeTweet(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt) throws UserException, TweetException{

        User user = userService.findUserProfileByJwt(jwt);
        Like like = likeService.likeTweet(tweetId, user);

        LikeDto likeDto = LikeDtoMapper.toLikeDto(like, user);


        return new ResponseEntity<LikeDto>(likeDto, HttpStatus.CREATED);
    }

    @GetMapping("tweet/{tweetId}")
    public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt) throws UserException, TweetException{

        User user = userService.findUserProfileByJwt(jwt);
        List<Like> likes = likeService.getAllLikes(tweetId);

        List<LikeDto> likeDtos = LikeDtoMapper.toLikeDtos(likes, user);

        return new ResponseEntity<>(likeDtos, HttpStatus.CREATED);
    }
}
