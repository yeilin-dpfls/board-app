package com.example.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationConsumer {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String DISCORD_WEBHOOK_URL = "https://discordapp.com/api/webhooks/1458292015908257885/nJ4uZ2mIgiBHailWCfx_EYM8NixNf2NTCnaglLyMRrO5ElhXD0a-GGm2m1-RSkGHhHWq";

    @KafkaListener(topics = "board-events", groupId = "notification-group")
    public void listenBoard(String message) {
        sendToDiscord("📝 **[새 게시글 알림]**\n내용: " + message);
    }

    @KafkaListener(topics = "member-events", groupId = "notification-group")
    public void listenMember(String message) {
        sendToDiscord("🎉 **[신규 회원 가입]**\n아이디: " + message);
    }

    private void sendToDiscord(String content) {
        Map<String, String> body = new HashMap<>();
        body.put("content", content);
        try {
            restTemplate.postForEntity(DISCORD_WEBHOOK_URL, body, String.class);
            System.out.println("✅ 디스코드 알림 전송 완료: " + content);
        } catch (Exception e) {
            System.err.println("❌ 디스코드 전송 실패: " + e.getMessage());
        }
    }
}