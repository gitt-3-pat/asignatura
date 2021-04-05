package info.jab.microservices.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Table("BOOK")
public class Book {

	@Id
	private Long id;

	private String name;

	private String isbn;

	private Double price;

	private LocalDate publishedDate;

}
