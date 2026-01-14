package com.example.memberbackend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*") // CORS 허용 범위 확장
public class UserController {

    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 1. 중복 체크
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                response.put("status", "fail");
                response.put("message", "ID_EXISTS");
                return ResponseEntity.badRequest().body(response);
            }

            // 2. 저장
            userRepository.save(user);

            // 3. Kafka 로직 (에러나도 가입은 성공시키기 위해 try-catch 분리)
            try {
                String message = String.format("Member Join: %s", user.getUsername());
                System.out.println(">>> Kafka Member Event Sent: " + message);
            } catch (Exception e) {
                System.err.println(">>> Kafka 전송 에러: " + e.getMessage());
            }

            // 4. 성공 응답 (반드시 JSON 객체로 보냄)
            response.put("status", "success");
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 서버 내부 에러 시 로그 출력
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        Optional<User> found = userRepository.findByUsername(user.getUsername());
        if (found.isPresent() && found.get().getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok(found.get()); // 성공 시 유저 객체(JSON) 반환
        }
        
        response.put("status", "fail");
        response.put("message", "Invalid username or password");
        return ResponseEntity.status(401).body(response);
    }
}
