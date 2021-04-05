package info.jab.microservices.repository;

import info.jab.microservices.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Query("SELECT ls.id, ls.name, ls.min_age, ls.max_age, " +
            "h.handbuch_id AS manual_handbuch_id, h.author AS manual_author, h.text AS manual_text " +
            "FROM lego_set ls JOIN handbuch h ON ls.id = h.handbuch_id " +
            "WHERE name = :name")
    List<Book> findByName(@Param("name") String name);

}
