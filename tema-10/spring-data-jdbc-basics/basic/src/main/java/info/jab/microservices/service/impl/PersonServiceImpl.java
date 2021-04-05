package info.jab.microservices.service.impl;

import info.jab.microservices.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addBulkData() {
        jdbcTemplate.execute("INSERT INTO PERSON(FIRST_NAME, LAST_NAME) VALUES ('Victor', 'Hugo')");
        jdbcTemplate.execute("INSERT INTO Person(FIRST_NAME, LAST_NAME) VALUES ('Dante', 'Alighieri')");
        jdbcTemplate.execute("INSERT INTO Person(FIRST_NAME, LAST_NAME) VALUES ('Stefan', 'Zweig')");
        jdbcTemplate.execute("INSERT INTO Person(FIRST_NAME, LAST_NAME) VALUES ('Oscar', 'Wilde')");
    }

}