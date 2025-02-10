package com.certTrack.NotificationService.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class NotificationControllerTest {

	@Autowired
	MockMvc api;
	/*
	 * i use manual testing because here nothing to test, i just send messages and test manually
	 */

}
