package com.certTrack.NotificationService.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.certTrack.NotificationService.Configuration.TokenGenerator;
import com.certTrack.NotificationService.Entity.Notification;
import com.certTrack.NotificationService.Repository.NotificationRepository;

import ch.qos.logback.core.subst.Token;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
public class NotificationService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private JavaMailSender javaMailSender;
	//@Autowired
	//private TokenGenerator tokenGenerator;
	@Autowired
	private NotificationRepository notificationRepository;
	
	
	public void sendsomemessage(Long userId, 
								Long courseId, 
								String type) throws MessagingException {
		
		String queryForUserName = "SELECT email FROM users WHERE id = ?"; 
		String toemail = jdbcTemplate.queryForObject(queryForUserName, String.class, userId);
		
		String queryForCourseName = "SELECT name FROM course WHERE id = ?"; 
		String courseName = jdbcTemplate.queryForObject(queryForCourseName, String.class, userId);
		
		//get certificate file
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwiZXhwIjoxNzQ5OTQ0NjYzLCJlIjoiZGltYTY4MzY3NTNAZ21haWwuY29tIiwiYSI6WyJST0xFX0FETUlOIl19.1szC99iZjBONYspFlZrtLxKVIkSn4i2eLDGJT-LuoGI"; //tokenGenerator.generateServiceToken(Integer.valueOf(userId + ""));
		headers.setBearerAuth(token);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		String url = "http://localhost:8083/certifications/usercourse?userId="+userId+"&courseId="+courseId;
		ResponseEntity<ByteArrayResource> certificate = restTemplate.exchange(url, HttpMethod.GET, entity, ByteArrayResource.class);
		ByteArrayResource certificateFile = certificate.getBody();

		String body;
		String subject;
		
		if(type.equals("1")) {
			subject = "Completion of the java for beginers course";
			body = "Hello, we congratulate you on completing the course" + courseName + "\n"
					+ "Also attached to this letter is your certificate!";
		}else {
			subject = "default subject";
			body = "default body";
		}
		
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("dima6836753@gmail.com");
		helper.setTo(toemail);
		helper.setText(body);
		helper.setSubject(subject);
		helper.addAttachment("certificate.pdf", certificateFile);
		
		javaMailSender.send(message);
		notificationRepository.save(new Notification(userId, body, new Date()));
	}
}
