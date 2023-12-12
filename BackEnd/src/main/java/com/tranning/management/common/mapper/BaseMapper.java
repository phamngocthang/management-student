package com.tranning.management.common.mapper;

public interface BaseMapper<E, D>{
    D entityToDto(E entity);

    E dtoToEntity(D dto);

    void entityToDto(E entity, D dto);

    void dtoToEntity(D dto, E entity);
}
