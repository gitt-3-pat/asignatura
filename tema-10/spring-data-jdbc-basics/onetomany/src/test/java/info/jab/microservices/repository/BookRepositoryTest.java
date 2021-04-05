package info.jab.microservices.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;

import info.jab.microservices.model.Author;
import info.jab.microservices.model.AuthorRef;
import info.jab.microservices.model.Book;
import info.jab.microservices.repository.AuthorRepository;
import info.jab.microservices.repository.BookRepository;
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
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@Test
	public void createBookWithAuthor() {

		Author author = Author.builder()
				.firstName("John")
				.lastName("Miller")
				.dateOfBirth(LocalDate.of(1972, 03, 01))
				.build();

		authorRepository.save(author);

		Book book = Book.builder()
				.name("My Book")
				.isbn("ISBN1234")
				.publishedDate(LocalDate.of(2018, 12, 12))
				.price(12.99).build();
		book.addAuthor(authorRepository.save(author));

		bookRepository.save(book);

		Book savedBook = bookRepository.findById(book.getId()).orElseThrow(RuntimeException::new);
		assertThat(savedBook.getAuthorRefs()).size().isEqualTo(1);
	}

	@Test
	public void createBookWithMultipleAuthors() {

		Author author1 = Author.builder().firstName("John").lastName("Miller").dateOfBirth(LocalDate.of(1972, 03, 01))
				.build();
		Author author2 = Author.builder().firstName("John").lastName("Miller").dateOfBirth(LocalDate.of(1972, 03, 01))
				.build();
		Book book2 = Book.builder().name("My Book").isbn("ISBN1234").publishedDate(LocalDate.of(2018, 12, 12))
				.price(12.99).build();
		book2.addAuthor(authorRepository.save(author1));
		book2.addAuthor(authorRepository.save(author2));
		bookRepository.save(book2);
		Book savedBook = bookRepository.findById(book2.getId()).orElseThrow(RuntimeException::new);
		assertThat(savedBook.getAuthorRefs()).size().isEqualTo(2);

		savedBook.getAuthorRefs().stream()
				.map(AuthorRef::toString)
				.forEach(LOGGER::info);

		LOGGER.info("{}", bookRepository.count());

	}

}
