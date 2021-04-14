package info.jab.microservices.repository;

import info.jab.microservices.model.Credentials;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import info.jab.microservices.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT count(*) FROM USER_CREDENTIALS WHERE USER_CREDENTIALS.USER_NAME= :username ")
	long findByUserName(@Param("username") String username);

	@Query("SELECT * FROM USER")
	List<User> getU();

	@Query("SELECT * FROM USER_CREDENTIALS")
	List<Credentials> getUC();
}
