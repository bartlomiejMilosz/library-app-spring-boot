package io.bartmilo.database.dao.impl;

import io.bartmilo.database.TestDataUtil;
import io.bartmilo.database.domain.Author;
import io.bartmilo.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookDaoImplIntegrationTests {
    private final AuthorDaoImpl authorDao;
    private final BookDaoImpl underTest;

    @Autowired
    public BookDaoImplIntegrationTests(AuthorDaoImpl authorDao, BookDaoImpl underTest) {
        this.authorDao = authorDao;
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createSingleTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtil.createSingleTestBook();
        book.setAuthorId(author.getId());

        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        List<Author> listOfAuthors = TestDataUtil.createListOfTestAuthors();
        authorDao.create(listOfAuthors.get(0));
        authorDao.create(listOfAuthors.get(1));
        authorDao.create(listOfAuthors.get(2));

        List<Book> listOfBooks = TestDataUtil.createListOfTestBooks();
        underTest.create(listOfBooks.get(0));
        underTest.create(listOfBooks.get(1));
        underTest.create(listOfBooks.get(2));

        List<Book> result = underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(
                        listOfBooks.get(0),
                        listOfBooks.get(1),
                        listOfBooks.get(2)
                );
    }
}
