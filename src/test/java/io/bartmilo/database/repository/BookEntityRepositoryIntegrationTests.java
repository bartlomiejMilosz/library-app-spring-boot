package io.bartmilo.database.repository;

import io.bartmilo.database.TestDataUtil;
import io.bartmilo.database.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryIntegrationTests {
    private final BookRepository underTest;

    @Autowired
    public BookEntityRepositoryIntegrationTests(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        BookEntity bookEntity = TestDataUtil.createSingleTestBookEntity();

        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        List<BookEntity> listOfBookEntities = TestDataUtil.createListOfTestBooksEntities();
        underTest.save(listOfBookEntities.get(0));
        underTest.save(listOfBookEntities.get(1));
        underTest.save(listOfBookEntities.get(2));

        Iterable<BookEntity> result = underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(
                        listOfBookEntities.get(0),
                        listOfBookEntities.get(1),
                        listOfBookEntities.get(2)
                );
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        BookEntity bookEntity = TestDataUtil.createSingleTestBookEntity();
        underTest.save(bookEntity);

        bookEntity.setTitle("UPDATED");

        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
        assertThat(result.get().getTitle()).isEqualTo("UPDATED");
    }

    @Test
    public void testThatBookCanBeDeleted() {
        BookEntity bookEntity = TestDataUtil.createSingleTestBookEntity();
        underTest.save(bookEntity);

        underTest.deleteById(bookEntity.getIsbn());
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());

        assertThat(result).isEmpty();
    }
}
