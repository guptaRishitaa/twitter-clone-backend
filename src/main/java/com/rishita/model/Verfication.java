package com.rishita.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Verfication {

    private boolean status=false;
    private LocalDateTime startedAt;
    private LocalDateTime endsAt;
    private String planType;
}
