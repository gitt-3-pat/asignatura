package info.jab.microservices.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("BOOK_AUTHOR")
public class AuthorRef {

	private Long author;

}
