package io.bartmilo.database.dao;

import io.bartmilo.database.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);
    Optional<Author> findOne(long l);

    List<Author> findAll();

    void update(Author author, long id);
}
