package com.example.memberbackend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.kafka.core.KafkaTemplate;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 리액트 접속 허용
public class UserController {

    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    // 회원가입
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "fail: ID_EXISTS";
        }
        userRepository.save(user);

	try {
            String message = String.format("Member Join: %s", user.getUsername());
            kafkaTemplate.send("member-events", message);
            System.out.println(">>> Kafka Member Event Sent: " + message);
        } catch (Exception e) {
            System.err.println(">>> Kafka 전송 에러: " + e.getMessage());
        }

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
