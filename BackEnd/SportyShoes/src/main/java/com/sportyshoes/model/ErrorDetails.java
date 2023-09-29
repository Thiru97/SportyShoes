package com.sportyshoes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorDetails {

    private LocalDateTime timeStamp;
    private String message;
    private String details;
}

