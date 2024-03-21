package io.bartmilo.database.controllers;

import io.bartmilo.database.domain.dto.AuthorDto;
import io.bartmilo.database.domain.dto.BookDto;
import io.bartmilo.database.domain.entities.AuthorEntity;
import io.bartmilo.database.domain.entities.BookEntity;
import io.bartmilo.database.mappers.Mapper;
import io.bartmilo.database.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;
    private final Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto book
    ) {
        BookEntity bookEntity = bookMapper.mapFrom(book);
        BookEntity savedBookEntity = bookService.save(isbn, bookEntity);
        return new ResponseEntity<>(
                bookMapper.mapTo(savedBookEntity),
                HttpStatus.CREATED
        );
    }

    /*@GetMapping(path = "/books")
    public ResponseEntity<List<BookDto>> listBooks(Pageable pageable) {
        Page<BookEntity> books = bookService.findAll(pageable);
        List<BookDto> listOfBooksDto = books.stream()
                .map(bookMapper::mapTo)
                .toList();
        return new ResponseEntity<>(listOfBooksDto, HttpStatus.OK);
    }*/

    @GetMapping(path = "/books")
    public ResponseEntity<Page<BookDto>> listBooks(Pageable pageable) {
        Page<BookEntity> bookEntities = bookService.findAll(pageable);
        Page<BookDto> bookDto = bookEntities.map(bookMapper::mapTo);
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> findBookByIsbn(@PathVariable("isbn") String isbn) {
        BookEntity bookEntity = bookService.findByIsbn(isbn)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Book not found with ISBN: " + isbn
                ));
        BookDto bookDto = bookMapper.mapTo(bookEntity);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }
}
