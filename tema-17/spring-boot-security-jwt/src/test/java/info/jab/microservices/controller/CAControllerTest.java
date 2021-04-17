package info.jab.microservices.controller;

import info.jab.microservices.model.GlobalErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static info.jab.microservices.Utils.asJsonString;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CAControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void given_bad_endpoint_when_call_it_then_message_ok() throws Exception {

        //Given
        String expectedResult = "Contact with the operator";
        GlobalErrorResponse expectedResponse = new GlobalErrorResponse(expectedResult);

        //When
        //Then
        this.mockMvc
                .perform(get("/katakroker").contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value(expectedResult))
                .andExpect(content().string(containsString(asJsonString(expectedResponse))));
    }

    @TestConfiguration
    public static class TestCfg {

        @Configuration
        @Order(1000)
        public class DisablingSecurityConfiguration extends WebSecurityConfigurerAdapter {

            @Override
            public void configure(WebSecurity web) {
                web.ignoring().antMatchers("/**");
            }
        }

        @RestController
        public class ForcingExceptionController {

            @GetMapping("/katakroker")
            public String hello() {
                throw new RuntimeException("Boom!");
            }
        }
    }
}