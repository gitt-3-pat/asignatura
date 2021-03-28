package info.jab.microservices;

import com.github.tomakehurst.wiremock.WireMockServer;
import info.jab.microservices.model.City;
import info.jab.microservices.model.JwtRequest;
import info.jab.microservices.model.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//Ideas from:
//https://spring.io/guides/gs/testing-web/
//https://www.baeldung.com/integration-testing-in-spring

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CitiesControllerE2ETest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	WireMockServer wireMockServer;

	@BeforeEach
	public void setup () {
		wireMockServer = new WireMockServer(8090);
		wireMockServer.start();
	}

	@AfterEach
	public void teardown () {
		wireMockServer.stop();
	}

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
	public void given_app_when_call_cities_then_ok() {

		//Given
		String token = getToken("user", "password");

		wireMockServer.stubFor(get(urlEqualTo("/municipio_comunidad_madrid.json"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json")
						.withStatus(200)
						.withBodyFile("madrid_open_data.json")));

		//When
		String address = "http://localhost:" + port + "/api/cities/";
		ResponseEntity<List<City>> result = restTemplate.exchange(
				address, HttpMethod.GET, getHeaders(token), new ParameterizedTypeReference<List<City>>() {});

		//Then
		then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void given_app_when_call_city_then_ok() {

		//Given
		String token = getToken("user", "password");

		wireMockServer.stubFor(get(urlEqualTo("/municipio_comunidad_madrid.json"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json")
						.withStatus(200)
						.withBodyFile("madrid_open_data.json")));

		//When
		String id = String.valueOf(148);
		String address = "http://localhost:" + port + "/api/cities/" + id;
		ResponseEntity<City> result = restTemplate.exchange(
				address, HttpMethod.GET, getHeaders(token), new ParameterizedTypeReference<City>() {});

		//Then
		then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	private HttpEntity getHeaders(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		return new HttpEntity(headers);
	}
}