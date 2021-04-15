package info.jab.microservices.service.impl;

import info.jab.microservices.model.APIResponse;
import info.jab.microservices.model.City;
import info.jab.microservices.service.MadridService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MadridCitiesServiceImpl implements MadridService {

    @Value("${endpoint}")
    private String endpoint;

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<APIResponse> getAPIData() {
        return restTemplate.getForEntity(endpoint, APIResponse.class);
    }

    @Override
    public List<City> getCities() {
        return getAPIData().getBody().getData();
    }

    @Override
    public Optional<City> getCity(String id) {
        return getAPIData().getBody().getData().stream()
                .filter(data -> data.getMunicipioCodigo().equals(id))
                .findFirst();
    }
}
