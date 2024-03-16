package io.bartmilo.database.dao.impl;

import io.bartmilo.database.TestDataUtil;
import io.bartmilo.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {
    @Mock
    private JdbcTemplate jdbcTemplateMock;
    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSQL() {
        Book book = TestDataUtil.createSingleTestBook();

        underTest.create(book);

        verify(jdbcTemplateMock).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("978-83-64640-14-8"),
                eq("Elementor Fundamentals"),
                eq(1L)
        );
    }

    @Test
    public void testThatFindOneBookGeneratesCorrectSQL() {
        underTest.findOne("978-83-64640-14-8");

        verify(jdbcTemplateMock).query(
                eq("SELECT * FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("978-83-64640-14-8")
        );
    }

    @Test
    public void testThatFindListOfBooksGeneratesTheCorrectSql() {
        underTest.findAll();

        verify(jdbcTemplateMock).query(
                eq("SELECT * FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGenerateCorrectSQL() {
        Book book = TestDataUtil.createSingleTestBook();
        underTest.update(book, book.getIsbn());

        verify(jdbcTemplateMock).update(
                eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"),
                eq("978-83-64640-14-8"),
                eq("Elementor Fundamentals"),
                eq(1L),
                eq(book.getIsbn())
        );
    }
}
