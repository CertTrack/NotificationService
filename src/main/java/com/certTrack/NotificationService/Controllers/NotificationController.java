package com.certTrack.NotificationService.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.certTrack.NotificationService.DTO.ResponseMessage;
import com.certTrack.NotificationService.Service.NotificationService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseMessage sendMessage(@RequestParam Long userId, 
    								   @RequestParam String message, 
    								   @RequestParam String subject) {
    	try {
			notificationService.sendsomemessage(userId, 
					message, 
					subject);
		} catch (MessagingException e) {
			return new ResponseMessage("somewhere problem when while message");
		}
    	return new ResponseMessage("succesfully send message");
    }
}