package com.example.memberbackend;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {

    @Id // 기본키(PK) 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 자동 증가(Auto Increment)
    private Long id;

    @Column(unique = true, nullable = false) // 아이디는 중복될 수 없고, 비어있으면 안 됩니다.
    private String username;

    @Column(nullable = false) // 비밀번호는 필수입니다.
    private String password;

    private String nickname; // 사용자의 별명
}