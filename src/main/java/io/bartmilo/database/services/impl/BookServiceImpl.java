package io.bartmilo.database.services.impl;

import io.bartmilo.database.domain.entities.BookEntity;
import io.bartmilo.database.repository.BookRepository;
import io.bartmilo.database.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookEntity save(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return repository.save(book);
    }

    @Override
    public List<BookEntity> findAll() {
        /* Functional solution */
        return StreamSupport.stream(repository
                                .findAll()
                                .spliterator(),
                        false
                )
                .toList();
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<BookEntity> findByIsbn(String isbn) {
        return repository.findById(isbn);
    }
}
