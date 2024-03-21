package io.bartmilo.database.mappers;

public interface Mapper<TypeA, TypeB> {
    TypeB mapTo(TypeA typeA);
    TypeA mapFrom(TypeB typeB);
}
