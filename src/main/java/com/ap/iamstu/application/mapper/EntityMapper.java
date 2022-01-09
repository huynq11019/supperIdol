package com.ap.iamstu.application.mapper;

import java.util.List;

/**
 * @param <D> - Domain type parameter.
 */

public interface EntityMapper<D, E> {

    D toDomain(E entity);

    List<D> toDomain(List<E> entities);

    E toEntity(D domain);

    List<E> toEntity(List<D> domains);
}
