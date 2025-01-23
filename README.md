# Notification Service

## Overview
The Notification Service is responsible for handling user notifications. It listens to messages from a Kafka topic and sends email notifications to users via SMTP. Notifications are saved in a database for future reference.

---

## Key Features
- **Kafka Consumer:** Listens to the `notification` topic and processes incoming messages.
- **SMTP Integration:** Sends email notifications using JavaMailSender.
- **Database Logging:** Logs sent notifications into the database for tracking purposes.

---

## Kafka Integration
The service uses a Kafka consumer to listen for notification messages.

### Topic
- **Name:** `notification`

### Consumer Details
- **Group ID:** `certTrack`
- **Container Factory:** `kafkaListenerContainerFactory`

### Example Message Structure
```json
{
  "userId": "123",
  "message": "You have completed the course!",
  "subject": "Course Completion"
}
```

The Kafka listener extracts the `userId`, `message`, and `subject` from the message payload and triggers the email sending process.

---

## Email Notification
The service uses `JavaMailSender` to send emails. Each email includes:
- **Recipient Email:** Retrieved from the `users` table based on the provided `userId`.
- **Subject:** Specified in the Kafka message.
- **Body:** Specified in the Kafka message.

### Email Sending Process
1. Query the recipient's email from the database:
   ```sql
   SELECT email FROM users WHERE id = ?
   ```
2. Create a MIME email message with the following details:
   - **From:** `dima6836753@gmail.com`
   - **To:** User's email
   - **Subject:** Provided subject
   - **Body:** Provided message body
3. Send the email using `JavaMailSender`.
4. Save the notification details in the database:
   ```java
   notificationRepository.save(new Notification(userId, body, new Date()));
   ```

---

## Notification Entity
The `Notification` entity is used to log notifications.

### Fields
- **userId (Long):** ID of the recipient user.
- **body (String):** Notification message body.
- **date (Date):** Timestamp of when the notification was sent.

### Example
```java
notificationRepository.save(new Notification(userId, body, new Date()));
```

---

## Security
- **Kafka Consumer Security:** Ensure secure configurations for the Kafka topic.
- **Email Security:** SMTP configurations and credentials must be secured and not exposed in the codebase.

---

## Notes
- Ensure the Kafka topic `notification` is properly configured and accessible.
- Verify that the SMTP server credentials (`JavaMailSender`) are correctly set up in the application.

---

## Future Improvements
- Add support for attachments in email notifications.
- Implement retry logic for failed email sending attempts.
- Enhance logging and monitoring of Kafka consumer events and email operations.
