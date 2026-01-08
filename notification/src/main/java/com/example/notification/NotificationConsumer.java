package com.example.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    // Board 서비스에서 오는 메시지 감시
    @KafkaListener(topics = "board-events", groupId = "notification-group")
    public void listenBoard(String message) {
        System.out.println("======= [NOTIFICATION: BOARD] =======");
        System.out.println("내용: " + message);
    }

    // Member 서비스에서 오는 메시지 감시
    @KafkaListener(topics = "member-events", groupId = "notification-group")
    public void listenMember(String message) {
        System.out.println("======= [NOTIFICATION: MEMBER] =======");
        System.out.println("내용: " + message);
    }
}