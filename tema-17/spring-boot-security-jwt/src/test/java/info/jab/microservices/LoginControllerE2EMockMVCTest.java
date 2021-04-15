package info.jab.microservices;

import info.jab.microservices.model.JwtRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static info.jab.microservices.Utils.asJsonString;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Ideas from:
//https://spring.io/guides/gs/testing-web/
//https://www.baeldung.com/integration-testing-in-spring

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerE2EMockMVCTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void given_valid_credential_when_login_then_receive_jwt_token() throws Exception {

		//Given
		JwtRequest jwtRequest = new JwtRequest("user", "password");

		//When
		//Then
		this.mockMvc
				.perform(post("/api/login")
						.contentType(APPLICATION_JSON_VALUE)
						.content(asJsonString(jwtRequest)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.jwttoken").exists())
				.andExpect(jsonPath("$.jwttoken").isNotEmpty())
				.andExpect(jsonPath("$.jwttoken").isString());
	}

	@Test
	public void given_bad_credential_when_login_then_Ko() throws Exception {

		//Given
		JwtRequest jwtRequest = new JwtRequest(
				"olduser",
				"oldpassword");

		//When
		//Then
		this.mockMvc
				.perform(post("/api/login")
						.contentType(APPLICATION_JSON_VALUE)
						.content(asJsonString(jwtRequest)))
				.andDo(print())
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void given_null_request_when_login_then_Ko() throws Exception {

		//Given
		JwtRequest jwtRequest = new JwtRequest();

		//When
		//Then
		this.mockMvc
				.perform(post("/api/login")
						.contentType(APPLICATION_JSON_VALUE)
						.content(asJsonString(jwtRequest)))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

}