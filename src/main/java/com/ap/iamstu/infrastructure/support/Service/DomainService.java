package com.ap.iamstu.infrastructure.support.Service;

import java.util.List;
import java.util.Optional;

public interface DomainService<D, I> {

    Optional<D> findById(I id);

    List<D> findAllByIds(List<I> ids);

    D save(D event);

    List<D> saveAll(List<D> events);

}
