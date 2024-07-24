package com.example.demo.Request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateEventRequest {
    private String summary;
    private String location;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
