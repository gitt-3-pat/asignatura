package info.jab.microservices.repository;

import static org.assertj.core.api.BDDAssertions.then;

import java.time.LocalDate;

import info.jab.microservices.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;

	@Test
	public void createBookWithAuthor() {

		Book book = Book.builder()
				.name("My Book")
				.isbn("ISBN1234")
				.publishedDate(LocalDate.of(2018, 12, 12))
				.price(12.99).build();

		bookRepository.save(book);

		Book savedBook = bookRepository.findById(book.getId()).orElseThrow(RuntimeException::new);
		then(savedBook.getId()).isNotNull();
	}
}
