package com.rishita.controller;

import com.rishita.service.CustomUserDetailsServiceImplementation;
import com.rishita.config.JwtProvider;
import com.rishita.exception.UserException;
import com.rishita.model.User;
import com.rishita.model.Verfication;
import com.rishita.repository.UserRepository;
import com.rishita.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
   private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetailsServiceImplementation customUserDetails;

//    @GetMapping("/testTwi")
//    public String testAppTwitter(){
//        return "I'm running";
//    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

        String email= user.getEmail();
        String password= user.getPassword();
        String fullName= user.getFullName();
        String birthDate= user.getBirthDate();

//        checking if there already exists a user with this email
        User isEmailExist=userRepository.findByEmail(email);

        if(isEmailExist!=null){
            throw new UserException("Email is already used with another account");
        }

        User createdUser=new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setBirthDate(birthDate);
        createdUser.setVerification(new Verfication());

//        saving this user
        User savedUser= userRepository.save(createdUser);

//        generating token
        Authentication authentication=new UsernamePasswordAuthenticationToken(email,password);
//      setting in security context holder
        SecurityContextHolder.getContext().setAuthentication(authentication);
//generating Jwt
        String token=jwtProvider.generateToken(authentication);

        AuthResponse response=new AuthResponse(token,true);

        return new ResponseEntity<AuthResponse>(response, HttpStatus.CREATED);
    }

   @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody User user){
        String username= user.getEmail();
        String password= user.getPassword();

        Authentication authentication=authenticate(username,password);
//        once we get authentication, it means our password matched

        String token=jwtProvider.generateToken(authentication);

        AuthResponse response=new AuthResponse(token,true);

        return new ResponseEntity<AuthResponse>(response, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String username, String password) {
//        checking email and then checking password
        UserDetails userDetails=customUserDetails.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username...");

        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
