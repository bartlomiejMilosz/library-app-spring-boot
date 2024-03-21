package io.bartmilo.database;

import io.bartmilo.database.domain.dto.AuthorDto;
import io.bartmilo.database.domain.dto.BookDto;
import io.bartmilo.database.domain.entities.AuthorEntity;
import io.bartmilo.database.domain.entities.BookEntity;

import java.util.List;

public final class TestDataUtil {
    private TestDataUtil() {

    }

    public static AuthorDto createSingleTestAuthorDto() {
        return AuthorDto.builder()
                .id(1L)
                .name("Carol Raccoon")
                .age(25)
                .build();
    }

    public static BookDto createSingleTestBookDto(AuthorDto author) {
        return BookDto.builder()
                .isbn("978-83-64640-14-8")
                .title("Elementor Fundamentals")
                .author(author)
                .build();
    }

    public static AuthorEntity createSingleTestAuthorEntity() {
        return AuthorEntity.builder()
                .id(1L)
                .name("Carol Raccoon")
                .age(25)
                .build();
    }

    public static List<AuthorEntity> createListOfTestAuthorsEntities() {
        List<AuthorEntity> listOfAuthorEntities;
        AuthorEntity firstAuthorEntity = createSingleTestAuthorEntity();

        AuthorEntity secondAuthorEntity = AuthorEntity.builder()
                .id(2L)
                .name("Matty Gpt")
                .age(27)
                .build();

        AuthorEntity thirdAuthorEntity = AuthorEntity.builder()
                .id(3L)
                .name("Michael Cyrus")
                .age(41)
                .build();

        listOfAuthorEntities = List.of(firstAuthorEntity, secondAuthorEntity, thirdAuthorEntity);
        return listOfAuthorEntities;
    }

    public static BookEntity createSingleTestBookEntity() {
        return BookEntity.builder()
                .isbn("978-83-64640-14-8")
                .title("Elementor Fundamentals")
                .authorEntity(createSingleTestAuthorEntity())
                .build();
    }

    public static List<BookEntity> createListOfTestBooksEntities() {
        List<BookEntity> listOfBookEntities;

        BookEntity firstBookEntity = createSingleTestBookEntity();

        BookEntity secondBookEntity = BookEntity.builder()
                .isbn("878-83-64540-17-1")
                .title("How To Develop SaaS App That Sells")
                .authorEntity(createListOfTestAuthorsEntities().get(1))
                .build();

        BookEntity thirdBookEntity = BookEntity.builder()
                .isbn("921-34-64320-12-3")
                .title("From Nerf Guns To Software House")
                .authorEntity(createListOfTestAuthorsEntities().get(2))
                .build();

        listOfBookEntities = List.of(firstBookEntity, secondBookEntity, thirdBookEntity);

        return listOfBookEntities;
    }
}
