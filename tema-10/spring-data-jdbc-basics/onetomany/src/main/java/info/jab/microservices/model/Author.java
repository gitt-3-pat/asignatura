package info.jab.microservices.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@Table("AUTHOR")
public class Author extends Auditable {

	@Id
	private Long id;

	private String firstName;

	private String lastName;

	private LocalDate dateOfBirth;

}
