package io.bartmilo.database.mappers.impl;

import io.bartmilo.database.domain.dto.BookDto;
import io.bartmilo.database.domain.entities.BookEntity;
import io.bartmilo.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {
    private final ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper objectMapper) {
        this.modelMapper = objectMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
