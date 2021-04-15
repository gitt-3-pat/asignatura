package info.jab.microservices.controller;

import info.jab.microservices.model.CheckResponse;
import info.jab.microservices.model.JwtRequest;
import info.jab.microservices.model.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static info.jab.microservices.Utils.asJsonString;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getToken(String username, String password) {

        //Given
        String address = "http://localhost:" + port + "/api/login";
        JwtRequest jwtRequest = new JwtRequest(username, password);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<JwtRequest> request = new HttpEntity<>(jwtRequest, headers);

        //When
        ResponseEntity<JwtResponse> result = this.restTemplate.postForEntity(address, request, JwtResponse.class);

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody().getJwttoken()).isNotEmpty();

        return result.getBody().getJwttoken();
    }

    @Test
    public void given_user_with_admin_roles_when_check_then_Ok() throws Exception {

        //Given
        String token = getToken("admin", "password");

        //When
        String address = "http://localhost:" + port + "/api/check";
        ResponseEntity<CheckResponse> result = restTemplate.exchange(
                address, HttpMethod.GET, getHeaders(token), CheckResponse.class);

        //Then
        String expectedResult = "Ok";
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody().getResult()).isEqualTo(expectedResult);
    }


    @Test
    public void given_user_without_admin_roles_when_check_then_Ko() throws Exception {

        //Given
        String token = getToken("user", "password");

        //When
        String address = "http://localhost:" + port + "/api/check";
        ResponseEntity<CheckResponse> result = restTemplate.exchange(
                address, HttpMethod.GET, getHeaders(token), CheckResponse.class);

        //Then
        String expectedResult = "Ko";
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody().getResult()).isEqualTo(expectedResult);
    }

    private HttpEntity getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return new HttpEntity(headers);
    }
}