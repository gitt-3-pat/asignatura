package info.jab.microservices.service;

import info.jab.microservices.model.City;

import java.util.List;
import java.util.Optional;

public interface MadridService {

    List<City> getCities();
    Optional<City> getCity(String id);
}
