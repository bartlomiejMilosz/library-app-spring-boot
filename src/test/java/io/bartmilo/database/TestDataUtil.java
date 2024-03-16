package io.bartmilo.database;

import io.bartmilo.database.domain.Author;
import io.bartmilo.database.domain.Book;

import java.util.ArrayList;
import java.util.List;

public final class TestDataUtil {
    private TestDataUtil() {

    }

    public static Author createSingleTestAuthor() {
        return Author.builder()
                .id(1L)
                .name("Carol Raccoon")
                .age(25)
                .build();
    }

    public static List<Author> createListOfTestAuthors() {
        List<Author> listOfAuthors;
        Author firstAuthor = createSingleTestAuthor();

        Author secondAuthor = Author.builder()
                .id(2L)
                .name("Matty Gpt")
                .age(27)
                .build();

        Author thirdAuthor = Author.builder()
                .id(3L)
                .name("Michael Cyrus")
                .age(41)
                .build();

        listOfAuthors = List.of(firstAuthor, secondAuthor, thirdAuthor);
        return listOfAuthors;
    }

    public static Book createSingleTestBook() {
        return Book.builder()
                .isbn("978-83-64640-14-8")
                .title("Elementor Fundamentals")
                .authorId(1L)
                .build();
    }

    public static List<Book> createListOfTestBooks() {
        List<Book> listOfBooks;

        Book firstBook = createSingleTestBook();

        Book secondBook = Book.builder()
                .isbn("878-83-64540-17-1")
                .title("How To Develop SaaS App That Sells")
                .authorId(2L)
                .build();

        Book thirdBook = Book.builder()
                .isbn("921-34-64320-12-3")
                .title("From Nerf Guns To Software House")
                .authorId(3L)
                .build();

        listOfBooks = List.of(firstBook, secondBook, thirdBook);

        return listOfBooks;
    }
}
