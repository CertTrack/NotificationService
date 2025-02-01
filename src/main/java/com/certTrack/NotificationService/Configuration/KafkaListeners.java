package com.certTrack.NotificationService.Configuration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.certTrack.NotificationService.Service.NotificationService;

import jakarta.mail.MessagingException;

@Component
public class KafkaListeners {

    @Autowired
    NotificationService notificationService;

    @KafkaListener(topics = "notification", groupId = "certTrack", containerFactory = "kafkaListenerContainerFactory")
    public void listener(Map<String, String> message) throws MessagingException {
        Long userId = Long.valueOf(message.get("userId"));
        String msg = message.get("message");
        String subject = message.get("subject");
        notificationService.sendsomemessage(userId, msg, subject);
    }
}

