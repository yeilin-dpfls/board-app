package com.example.demo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Board {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private String title;   // 제목
    private String content; // 내용

    @ManyToOne
    @JoinColumn(name = "user_id") // DB의 user_id 컬럼과 연결
    private User writer;
}
