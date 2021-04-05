package info.jab.microservices.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("PERSON")
public class Person {

    @Id
    private Long id;

    private String first_name;

    private String last_name;
}
