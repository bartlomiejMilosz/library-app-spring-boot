package io.bartmilo.database.dao.impl;

import io.bartmilo.database.dao.AuthorDao;
import io.bartmilo.database.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        jdbcTemplate.update(
                "INSERT INTO authors (id, name, age) VALUES (?, ?, ?)",
                author.getId(),
                author.getName(),
                author.getAge()
        );
    }

    @Override
    public Optional<Author> findOne(long authorId) {
        List<Author> results = jdbcTemplate.query(
                "SELECT * FROM authors WHERE id = ? LIMIT 1",
                new AuthorRowMapper(),
                authorId
        );
        return results.stream().findFirst();
    }

    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM authors",
                new AuthorRowMapper()
        );
    }


    /**
     * <h2>RowMapper and Its Role</h2>
     * <p>
     * The <code>RowMapper</code> is a strategy interface that specifies how to map a row in a <code>ResultSet</code>
     * to a specific object (in this case, a <code>Person</code> object). Here's how it works in the context of this application:
     * </p>
     *
     * <ul>
     *   <li>
     *     When you perform a query using <code>JdbcTemplate</code>, you can specify a <code>RowMapper</code> to convert the ResultSet rows
     *     into Java objects.
     *   </li>
     *   <li>
     *     The <code>RowMapper</code> takes each row of the <code>ResultSet</code> and translates it into an instance of the specified class
     *     (Person in this scenario).
     *   </li>
     *   <li>
     *     For each row in the result set, the <code>RowMapper</code>'s <code>mapRow</code> method is called. This method takes the current
     *     row's <code>ResultSet</code> and the row number as arguments.
     *   </li>
     *   <li>
     *     Inside <code>mapRow</code>, you typically create a new instance of your target object (Person) and set its properties
     *     using the values from the ResultSet. For example, you might call <code>resultSet.getLong("id")</code> to get the ID
     *     column's value and set it on the Person object.
     *   </li>
     * </ul>
     */
    public static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getNString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }
}
