package info.jab.microservices.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import info.jab.microservices.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT count(*) FROM USER_CREDENTIALS WHERE USER_CREDENTIALS.USER_NAME= :username ")
	public long findByUserName(@Param("username") String username);

}
