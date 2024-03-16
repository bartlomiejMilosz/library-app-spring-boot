package io.bartmilo.database.dao.impl;

import io.bartmilo.database.TestDataUtil;
import io.bartmilo.database.domain.Author;
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
public class AuthorDaoImplIntegrationTests {
    private final AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createSingleTestAuthor();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        List<Author> listOfAuthors = TestDataUtil.createListOfTestAuthors();

        underTest.create(listOfAuthors.get(0));
        underTest.create(listOfAuthors.get(1));
        underTest.create(listOfAuthors.get(2));

        List<Author> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(
                        listOfAuthors.get(0),
                        listOfAuthors.get(1),
                        listOfAuthors.get(2)
                );
    }
}

