package info.jab.microservices.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.jab.microservices.model.User;
import info.jab.microservices.repository.UserRepository;

@RestController
@RequestMapping("api")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping("users")
	private ResponseEntity<User> create(@RequestBody User user) {
		final User createdUser = userRepository.save(user);
		return ResponseEntity.ok().body(createdUser);
	}

	@PutMapping("users/{id}")
	private ResponseEntity<User> create(@RequestBody User user, @PathVariable("id") String id) {
		final User createdUser = userRepository.save(user);
		return ResponseEntity.ok().body(createdUser);
	}

	@GetMapping("users")
	private ResponseEntity<Iterable<User>> getUsers() {
		return ResponseEntity.ok().body(userRepository.findAll());
	}

}
