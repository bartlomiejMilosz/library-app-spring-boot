package io.bartmilo.database.repository;

import io.bartmilo.database.TestDataUtil;
import io.bartmilo.database.domain.entities.AuthorEntity;
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
public class AuthorEntityRepositoryIntegrationTests {
    private final AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createSingleTestAuthorEntity();
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        List<AuthorEntity> listOfAuthorEntities = TestDataUtil.createListOfTestAuthorsEntities();

        underTest.save(listOfAuthorEntities.get(0));
        underTest.save(listOfAuthorEntities.get(1));
        underTest.save(listOfAuthorEntities.get(2));

        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(
                        listOfAuthorEntities.get(0),
                        listOfAuthorEntities.get(1),
                        listOfAuthorEntities.get(2)
                );
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtil.createSingleTestAuthorEntity();
        underTest.save(authorEntity);

        authorEntity.setName("UPDATED");

        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
        assertThat(result.get().getName()).isEqualTo("UPDATED");
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        AuthorEntity authorEntity = TestDataUtil.createSingleTestAuthorEntity();
        underTest.save(authorEntity);

        underTest.deleteById(authorEntity.getId());
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan() {
        List<AuthorEntity> listOfTestAuthorsAuthorEntities = TestDataUtil.createListOfTestAuthorsEntities();

        underTest.save(listOfTestAuthorsAuthorEntities.get(0));
        underTest.save(listOfTestAuthorsAuthorEntities.get(1));
        underTest.save(listOfTestAuthorsAuthorEntities.get(2));

        Iterable<AuthorEntity> results = underTest.ageLessThan(30);
        assertThat(results).containsExactly(listOfTestAuthorsAuthorEntities.get(0), listOfTestAuthorsAuthorEntities.get(1));
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        List<AuthorEntity> listOfTestAuthorsAuthorEntities = TestDataUtil.createListOfTestAuthorsEntities();

        underTest.save(listOfTestAuthorsAuthorEntities.get(0));
        underTest.save(listOfTestAuthorsAuthorEntities.get(1));
        underTest.save(listOfTestAuthorsAuthorEntities.get(2));

        Iterable<AuthorEntity> results = underTest.findAuthorsWithAgeGreaterThan(40);
        assertThat(results).containsExactly(listOfTestAuthorsAuthorEntities.get(2));
    }
}

