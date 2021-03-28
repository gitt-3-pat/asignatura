package info.jab.microservices.controller;

import static info.jab.microservices.Utils.asJsonString;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.jab.microservices.model.APIResponse;
import info.jab.microservices.model.City;
import info.jab.microservices.service.MadridService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class CitiesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MadridService service;

    @SneakyThrows
    private List<City> getExpectedList() {

        File file = new ClassPathResource("madrid_open_data.json").getFile();
        String jsonString = new String(Files.readAllBytes(file.toPath()));

        ObjectMapper mapper = new ObjectMapper();
        APIResponse list = mapper.readValue(jsonString, new TypeReference<>(){});

        return list.getData();
    }

    @Test
    @WithMockUser(username="user", roles="USER")
    public void given_controller_when_getCities_then_Ok() throws Exception {

        //Given
        List<City> expectedList = getExpectedList();
        when(service.getCities()).thenReturn(expectedList);

        //When
        //Then
        this.mockMvc
                .perform(get("/api/cities")
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(expectedList))));
    }

    @Test
    @WithMockUser(username="user", roles="USER")
    public void given_controller_when_getCity_then_Ok() throws Exception {

        //Given
        String id = String.valueOf(148);
        List<City> expectedList = getExpectedList();
        City expectedCity = expectedList.stream()
                .filter(data -> data.getMunicipioCodigo().equals(id))
                .findFirst()
                .get();
        when(service.getCity(id)).thenReturn(Optional.of(expectedCity));

        //When
        //Then
        this.mockMvc
                .perform(get("/api/cities/" + id)
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(expectedCity))));
    }

    @Test
    @WithMockUser(username="user", roles="USER")
    public void given_controller_when_getCity_for_unknown_id_then_Ok() throws Exception {

        //Given
        String id = String.valueOf(999);
        when(service.getCity(id)).thenReturn(Optional.empty());

        //When
        //Then
        this.mockMvc
                .perform(get("/api/cities/" + id)
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}