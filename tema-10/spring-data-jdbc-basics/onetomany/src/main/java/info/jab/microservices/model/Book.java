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
public class Book extends Auditable {

	@Id
	private Long id;

	private String name;

	private String isbn;

	private Set<AuthorRef> authorRefs;

	private Double price;

	private LocalDate publishedDate;

	public void addAuthor(Author author) {
		if (authorRefs == null) {
			authorRefs = new HashSet<>();
		}
		authorRefs.add(createAuthorRef(author));
	}

	private AuthorRef createAuthorRef(Author author) {
		Assert.notNull(author, "Author must not be null");
		Assert.notNull(author.getId(), "Author id must not be null");
		AuthorRef authorRef = new AuthorRef();
		authorRef.setAuthor(author.getId());
		return authorRef;
	}

}
