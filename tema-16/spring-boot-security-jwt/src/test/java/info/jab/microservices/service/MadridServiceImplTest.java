package info.jab.microservices.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.jab.microservices.model.APIResponse;
import info.jab.microservices.model.City;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MadridServiceImplTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
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
    public void given_service_when_call_getCities_then_Ok() {

        //Given
        var expectedList = getExpectedList();
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(expectedList);
        when(restTemplate.getForEntity(anyString(), anyObject()))
                .thenReturn(ResponseEntity.ok().body(apiResponse));

        //When
        List<City> result = service.getCities();

        //Then
        then(result)
                .hasSize(expectedList.size())
                .hasSameElementsAs(expectedList);
        verify(restTemplate, times(1))
                .getForEntity(anyString(), anyObject());
    }

    @Test
    public void given_service_when_call_getCity_known_then_Ok() {

        //Given
        var expectedList = getExpectedList();
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(expectedList);
        when(restTemplate.getForEntity(anyString(), anyObject()))
                .thenReturn(ResponseEntity.ok().body(apiResponse));

        //When
        String id = String.valueOf(148); //Torrejon de Ardoz
        Optional<City> result = service.getCity(id);

        //Then
        then(result.isPresent()).isTrue();
        verify(restTemplate, times(1))
                .getForEntity(anyString(), anyObject());
    }

    @Test
    public void given_service_when_call_getCity_unknown_then_Ok() {

        //Given
        var expectedList = getExpectedList();
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(expectedList);
        when(restTemplate.getForEntity(anyString(), anyObject()))
                .thenReturn(ResponseEntity.ok().body(apiResponse));

        //When
        String id = String.valueOf(999); //Torrejon de Ardoz
        Optional<City> result = service.getCity(id);

        //Then
        then(result.isPresent()).isFalse();
        verify(restTemplate, times(1))
                .getForEntity(anyString(), anyObject());
    }

}