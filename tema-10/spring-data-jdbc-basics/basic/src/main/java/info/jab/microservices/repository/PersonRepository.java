package info.jab.microservices.repository;

import info.jab.microservices.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

}
