package info.jab.microservices.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebDocumentTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void given_index_when_parse_then_Ok() throws IOException {

        //Given
        String address = "http://localhost:" + port + "/index.html";

        //When
        ResponseEntity<String> result = this.restTemplate.getForEntity(address, String.class);

        var parser = Parser.htmlParser()
                .setTrackErrors(10); // Set the number of errors it can track. 0 by default so it's important to set that
        var dom = Jsoup.parse(result.getBody(), "" /* this is the default */, parser);

        //Then
        then(parser.getErrors()).hasSize(0);
    }

    @Test
    public void given_home_when_parse_then_Ok() throws IOException {

        //Given
        String address = "http://localhost:" + port + "/home.html";

        //When
        ResponseEntity<String> result = this.restTemplate.getForEntity(address, String.class);

        var parser = Parser.htmlParser()
                .setTrackErrors(10); // Set the number of errors it can track. 0 by default so it's important to set that
        var dom = Jsoup.parse(result.getBody(), "" /* this is the default */, parser);

        //Then
        then(parser.getErrors()).hasSize(0);
    }

    @Test
    public void given_city_when_parse_then_Ok() throws IOException {

        //Given
        String address = "http://localhost:" + port + "/city.html";

        //When
        ResponseEntity<String> result = this.restTemplate.getForEntity(address, String.class);

        var parser = Parser.htmlParser()
                .setTrackErrors(10); // Set the number of errors it can track. 0 by default so it's important to set that
        var dom = Jsoup.parse(result.getBody(), "" /* this is the default */, parser);

        //Then
        then(parser.getErrors()).hasSize(0);
    }

    @Test
    public void given_admin_when_parse_then_Ok() throws IOException {

        //Given
        String address = "http://localhost:" + port + "/admin.html";

        //When
        ResponseEntity<String> result = this.restTemplate.getForEntity(address, String.class);

        var parser = Parser.htmlParser()
                .setTrackErrors(10); // Set the number of errors it can track. 0 by default so it's important to set that
        var dom = Jsoup.parse(result.getBody(), "" /* this is the default */, parser);

        //Then
        then(parser.getErrors()).hasSize(0);
    }

    @Test
    public void given_logout_when_parse_then_Ok() throws IOException {

        //Given
        String address = "http://localhost:" + port + "/out.html";

        //When
        ResponseEntity<String> result = this.restTemplate.getForEntity(address, String.class);

        var parser = Parser.htmlParser()
                .setTrackErrors(10); // Set the number of errors it can track. 0 by default so it's important to set that
        var dom = Jsoup.parse(result.getBody(), "" /* this is the default */, parser);

        //Then
        then(parser.getErrors()).hasSize(0);
    }

}
