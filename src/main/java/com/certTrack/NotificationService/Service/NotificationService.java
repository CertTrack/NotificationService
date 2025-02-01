package com.certTrack.NotificationService.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.certTrack.NotificationService.Entity.Notification;
import com.certTrack.NotificationService.Repository.NotificationRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class NotificationService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	public void sendsomemessage(Long userId, 
								String body, 
								String subject) throws MessagingException {
		
		String query = "SELECT email FROM users WHERE id = ?"; 
		String toemail = jdbcTemplate.queryForObject(query, String.class, userId);
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("dima6836753@gmail.com");
		helper.setTo(toemail);
		helper.setText(body);
		helper.setSubject(subject);
	
		
		//FileSystemResource fileSystemResource = new FileSystemResource(new File(attchment));
		//helper.addAttachment(fileSystemResource .getFilename(), fileSystemResource);
		javaMailSender.send(message);
		notificationRepository.save(new Notification(userId, body, new Date()));
	}
}
