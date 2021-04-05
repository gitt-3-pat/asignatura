package info.jab.microservices.repository;

import info.jab.microservices.model.Author;
import info.jab.microservices.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
@SpringBootTest
@Transactional
public class AuthorRepositoryTest {

	@Autowired
	private AuthorRepository authorRepository;

	@Sql("/db/migration/V2_initial_test_data.sql")
	@Test
	public void given_app_when_load_sql_then_Ok() {

		//Given
		//When
		//Then
		then(authorRepository.count()).isEqualTo(1);
	}

	@Test
	public void given_aggregate_when_save_then_Ok() {

		//Given
		Author author = Author.builder()
				.firstName("John")
				.lastName("Miller")
				.dateOfBirth(LocalDate.of(1972, 03, 01))
				.build();

		//When
		authorRepository.save(author);

		//Then
		then(author.getId()).isNotNull();
		LOGGER.info("Author ID: {}", author.getId());

		then(authorRepository.count()).isEqualTo(1);
	}

	@Test
	public void given_aggregate_when_save_then_populateAuditFields() {

		//Given
		Author author = Author.builder()
				.firstName("John")
				.lastName("Miller")
				.dateOfBirth(LocalDate.of(1972, 03, 01))
				.build();

		//When
		authorRepository.save(author);

		//Then
		then(author.getCreatedBy()).isEqualTo("TEST");
		then(author.getCreatedDate()).isNotNull();
		then(author.getLastModifiedBy()).isEqualTo("TEST");
		then(author.getLastModifiedDate()).isNotNull();

		then(authorRepository.count()).isEqualTo(1);
	}
}
