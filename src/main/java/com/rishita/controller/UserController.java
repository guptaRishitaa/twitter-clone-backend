package com.rishita.controller;

import com.rishita.dto.UserDto;
import com.rishita.dto.mapper.UserDtoMapper;
import com.rishita.exception.UserException;
import com.rishita.model.User;
import com.rishita.service.UserService;
import com.rishita.util.UserUtils;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired

    @GetMapping("/profile")
    private ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {

        User user =  userService.findUserProfileByJwt(jwt);
        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setReq_user(true);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);

    }

    @GetMapping("/{userId}")
    private ResponseEntity<UserDto> getUserById(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException {

        User reqUser =  userService.findUserProfileByJwt(jwt);

        User user = userService.findUserById(userId);

        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setReq_user(UserUtils.isReqUser(reqUser,user));
        userDto.setFollowed(UserUtils.isFollowedByReqUser(reqUser, user));
        return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);

    }

    @GetMapping("/search")
    private ResponseEntity<List<UserDto>> searchUser(@RequestParam String query, @RequestHeader("Authorization") String jwt) throws UserException {

        User reqUser =  userService.findUserProfileByJwt(jwt);

        List<User> users = userService.searchUser(query);

        List<UserDto> userDtos = UserDtoMapper.toUserDtos(users);

        return new ResponseEntity<>(userDtos, HttpStatus.ACCEPTED);

    }

    @PutMapping("/update")
    private ResponseEntity<UserDto> updateUser(@RequestBody User req, @RequestHeader("Authorization") String jwt) throws UserException {

        User reqUser =  userService.findUserProfileByJwt(jwt);

       User user = userService.updateUser(reqUser.getId(), req);

        UserDto userDto = UserDtoMapper.toUserDto(user);

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);

    }

//   req user will follow this user
    @PutMapping("/{userId}/follow")
    private ResponseEntity<UserDto> followUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException {

        User reqUser =  userService.findUserProfileByJwt(jwt);

        User user = userService.followUser(userId,reqUser);

        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setFollowed(UserUtils.isFollowedByReqUser(reqUser, user));

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);

    }
}
