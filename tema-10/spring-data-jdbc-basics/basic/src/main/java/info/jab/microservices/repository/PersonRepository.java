package info.jab.microservices.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import info.jab.microservices.model.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

	@Query("UPDATE PERSON SET PERSON.FIRST_NAME= :userName WHERE PERSON.ID= :id ")
	@Modifying
	int updateUserNameById(@Param("userName") String userName, @Param("id") Long id);

	@Query("SELECT * FROM PERSON")
	List<Person> myQuery();
}
