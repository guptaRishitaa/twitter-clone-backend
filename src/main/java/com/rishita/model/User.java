package com.rishita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
//import com.fasterxml.jackson.annotate.JsonIgnore;


import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    while creating user, we won't have to give id, it will auto generate
    private Long id;

    private String fullName;
    private String location;
    private String website;
    private String birthDate;
    private String email;
    private String password;
    private String mobile;
    private String image;
    private String backgroundImage;
    private String bio;
    private boolean req_user;
    private boolean login_with_google;

//    jsonIgnore because we don't want this when we retrieve user data
    @JsonIgnore
//    1 user can have multiple tweets
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Tweet> tweet = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

//    because it's not an entity.
    @Embedded
    private Verfication verification;

//    1 user can follow multiple users and multiple followers can follow multiple users
    @ManyToMany
    @JsonIgnore
    private List<User> followers =  new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    private List<User> followings = new ArrayList<>();
}
