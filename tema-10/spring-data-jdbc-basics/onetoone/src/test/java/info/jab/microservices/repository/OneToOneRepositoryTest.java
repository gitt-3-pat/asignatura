package info.jab.microservices.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import info.jab.microservices.model.Credentials;
import info.jab.microservices.model.User;
import info.jab.microservices.model.User.UserType;

@SpringBootTest
public class OneToOneRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("One-to-One Mapping Test")
	void embeddedMappingTest() {
		final Credentials credentials = new Credentials();
		credentials.setUserName("peterm");
		credentials.setPassword("password");
		final User user = new User();
		user.setCreatedTime(new Date());
		user.setDateofBirth(new Date());
		user.setUserType(UserType.EMPLOYEE);
		user.setCredentials(credentials);
		final User createdUser = userRepository.save(user);
		assertTrue(createdUser != null);
		userRepository.delete(user);
		assertTrue(userRepository.findByUserName(user.getCredentials().getUserName()) == 0);
	}

}
