package info.jab.microservices.controller;

import info.jab.microservices.model.City;
import info.jab.microservices.service.MadridService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(
        path = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CitiesController {

    @Autowired
    private MadridService madridService;

    @GetMapping("/cities")
    public @ResponseBody ResponseEntity<List<City>> getCities() {
        return ResponseEntity.ok().body(madridService.getCities());
    }

    @GetMapping("/cities/{id}")
    public @ResponseBody ResponseEntity<City> getCity(
            @PathVariable("id") String id) {

        Optional<City> city = madridService.getCity(id);

        if(city.isPresent()) {
            return ResponseEntity.ok().body(city.get());
        }
        return ResponseEntity.notFound().build();
    }
}
