package io.bartmilo.database.dao.impl;

import io.bartmilo.database.TestDataUtil;
import io.bartmilo.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {
    @Mock
    private JdbcTemplate jdbcTemplateMock;
    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testThatCreateAuthorGeneratesCorrectSQL() {
        Author author = TestDataUtil.createSingleTestAuthor();

        underTest.create(author);

        verify(jdbcTemplateMock).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L),
                eq("Carol Raccoon"),
                eq(25)
        );
    }

    @Test
    public void testThatFindOneAuthorGeneratesCorrectSQL() {
        underTest.findOne(1L);

        verify(jdbcTemplateMock).query(
                eq("SELECT * FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testThatFindListOfAuthorsGeneratesTheCorrectSql() {
        underTest.findAll();
        verify(jdbcTemplateMock).query(
                eq("SELECT * FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGenerateCorrectSQL() {
        Author author = TestDataUtil.createSingleTestAuthor();
        underTest.update(author, author.getId());
        verify(jdbcTemplateMock).update(
                eq("UPDATE authors SET id = ?, name = ?, age= ? WHERE id = ?"),
                eq(1L),
                eq("Carol Raccoon"),
                eq(25),
                eq(1L)
        );
    }

    @Test
    public void testThatDeleteGeneratesTheCorrectSQL() {
        Author author = TestDataUtil.createSingleTestAuthor();
        underTest.delete(author.getId());
        verify(jdbcTemplateMock).update(
                eq("DELETE FROM authors where id = ?"),
                eq(1L)
        );
    }
}
