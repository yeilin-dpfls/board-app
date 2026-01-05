package com.example.memberbackend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 리액트 접속 허용
public class UserController {

    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "fail: ID_EXISTS";
        }
        userRepository.save(user);
        return "success";
    }

    // 로그인
    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        Optional<User> found = userRepository.findByUsername(user.getUsername());
        if (found.isPresent() && found.get().getPassword().equals(user.getPassword())) {
            return found.get(); // 성공 시 유저 정보 반환
        }
        return "fail";
    }
}