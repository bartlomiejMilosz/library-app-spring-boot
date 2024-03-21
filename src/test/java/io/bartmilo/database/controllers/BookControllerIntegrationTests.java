package io.bartmilo.database.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bartmilo.database.TestDataUtil;
import io.bartmilo.database.domain.dto.AuthorDto;
import io.bartmilo.database.domain.dto.BookDto;
import io.bartmilo.database.domain.entities.AuthorEntity;
import io.bartmilo.database.domain.entities.BookEntity;
import io.bartmilo.database.services.AuthorService;
import io.bartmilo.database.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        AuthorDto authorDto = TestDataUtil.createSingleTestAuthorDto();
        BookDto bookDto = TestDataUtil.createSingleTestBookDto(authorDto);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedBook() throws Exception {
        AuthorDto authorDto = TestDataUtil.createSingleTestAuthorDto();
        BookDto bookDto = TestDataUtil.createSingleTestBookDto(authorDto);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testThatGetBookReturnsHttpStatus200WhenBookExist() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createSingleTestBookEntity();
        bookService.save(testBookEntity.getIsbn(), testBookEntity);

        mockMvc.perform(
                get("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testThatGetBookReturnsHttpStatus404WhenNoBookExist() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createSingleTestBookEntity();

        mockMvc.perform(
                get("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void testThatGetBookReturnsBookWhenAuthorExist() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createSingleTestBookEntity();
        bookService.save(testBookEntity.getIsbn(), testBookEntity);

        mockMvc.perform(
                get("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                jsonPath("$.isbn").value(testBookEntity.getIsbn())
        ).andExpect(
                jsonPath("$.title").value(testBookEntity.getTitle())
        ).andExpect(
                jsonPath("$.author").value(testBookEntity.getAuthorEntity())
        );
    }
}
