package io.bartmilo.database.services.impl;

import io.bartmilo.database.domain.entities.AuthorEntity;
import io.bartmilo.database.repository.AuthorRepository;
import io.bartmilo.database.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuthorEntity save(AuthorEntity author) {
        return repository.save(author);
    }

    @Override
    public List<AuthorEntity> findAll() {
        Iterable<AuthorEntity> allAuthors = repository.findAll();
        Spliterator<AuthorEntity> spliterator = allAuthors.spliterator();
        Stream<AuthorEntity> stream = StreamSupport.stream(spliterator, false);
        return stream.toList();
    }

    @Override
    public Optional<AuthorEntity> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public boolean isExists(long id) {
        return  repository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        authorEntity.setId(id);

        return repository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            return repository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author does not exist"));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
