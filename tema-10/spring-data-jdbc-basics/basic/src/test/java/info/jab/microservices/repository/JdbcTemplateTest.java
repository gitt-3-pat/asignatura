package info.jab.microservices.repository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import info.jab.microservices.model.User;
import info.jab.microservices.model.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(scripts="/basic_mapping.sql") //to created DB tables and init sample DB data
@Transactional
public class JdbcTemplateTest {

	@Autowired
	private UserJdbcTemplateRepository userRepository;
	
	@Test
	void createUserTest() {
		
		int created = userRepository.save(getUser());
		
		assertTrue(created == 1);
	}
	
	@Test
	void updateUserTest() {
		
		// Read all users
		List<User> allUsers = (List<User>) userRepository.findAll();
		
		allUsers.forEach(user -> {
			user.setPassword("ABC123abc#");
			int updated = userRepository.update(user);
			
			assertTrue(updated == 1);
		});
		
	}
	
	@Test
	void sortByUserName() {

		// By user name in descending order
		Sort sort = Sort.by(Direction.fromString("DESC"), "USER_NAME");

		System.err.println(sort.toList().get(0));

		// All Users
		List<User> users = (List<User>) userRepository.findAll();

		// Sorted Users
		List<User> sortedUsers = userRepository.findAll(sort);

		List<User> userList = users.stream().sorted(Comparator.comparing(User::getUserName).reversed())
				.collect(Collectors.toList());

	}

	@Test
	void getByPageAndSize() {

		// total 12 users in sample data, set 5 users per page - total 3 pages
		PageRequest pageable = PageRequest.of(0, 5);

		// All users
		List<User> users = (List<User>) userRepository.findAll();

		// paged users - each page should have 5 users
		Page<User> pagedUsers = userRepository.findAll(pageable);

		assertTrue(pagedUsers.getTotalPages() == 3);
		assertTrue(pagedUsers.getContent().equals(users.subList(0, 5)));
	}

	@Test
	void getByPageAndSizeSortByUserName() {

		PageRequest pageable = PageRequest.of(0, 5, Direction.fromString("DESC"), "USER_NAME");
		System.err.println(pageable.getSort().toList().get(0));
		// All Users
		List<User> users = (List<User>) userRepository.findAll();

		Page<User> pagedUsers = userRepository.findAll(pageable);

		List<User> usersList = users.stream().sorted(Comparator.comparing(User::getUserName).reversed())
				.collect(Collectors.toList()).subList(0, 5);

		assertTrue(pagedUsers.getTotalPages() == 3);
		assertTrue(pagedUsers.getContent().equals(usersList));
	}
	
	@Test
	void deleteUserTest() {
		
		// Read all users
		List<User> allUsers = (List<User>) userRepository.findAll();
		
		allUsers.forEach(user -> {
			userRepository.delete(user);
			
			assertTrue(userRepository.findById(user.getId()).isEmpty());
		});
	
	}

	private User getUser() {
		
		User user = new User();
		user.setUserType(UserType.STUDENT);
		user.setUserName("PhilipX");
		user.setPassword("ABC123abc*");
		user.setDateofBirth(new Date());
		user.setCreatedTime(new Date());
		
		return user;
	}
}
