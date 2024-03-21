package io.bartmilo.database.repository;

import io.bartmilo.database.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends
        CrudRepository<BookEntity, String>,
        PagingAndSortingRepository<BookEntity, String> {
}
