package com.example.demo.model;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private Long id;
    private String title;
    private String content;
    private String username;
    private Long userId;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
