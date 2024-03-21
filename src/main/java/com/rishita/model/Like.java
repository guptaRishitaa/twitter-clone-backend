package com.rishita.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
//giving table name because like is a reserved keyword, so keeping table name as likes
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Tweet tweet;
}
