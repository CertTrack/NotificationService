package com.certTrack.NotificationService.Controllers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;

import com.certTrack.NotificationService.DTO.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class NotificationControllerTest {

	@Autowired
	MockMvc api;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	void UserCanSandMessage() throws Exception {
		ResponseMessage response = new ResponseMessage("succesfully send message");
		String responseJson = objectMapper.writeValueAsString(response);
		api.perform(post("/notification/send?userId=2&message=message+from+test&subject=test+subject"))
			.andExpect(content().json(responseJson));
	}

}
