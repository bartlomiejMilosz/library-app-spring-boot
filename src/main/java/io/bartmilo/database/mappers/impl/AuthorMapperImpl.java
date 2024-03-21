package io.bartmilo.database.mappers.impl;

import io.bartmilo.database.domain.dto.AuthorDto;
import io.bartmilo.database.domain.entities.AuthorEntity;
import io.bartmilo.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {
    private ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
