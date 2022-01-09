package com.ap.iamstu.infrastructure.support.Service;

import com.ap.iamstu.application.mapper.EntityMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractDomainService<D, E, I> implements DomainService<D, I> {

    protected final JpaRepository<E, I> jpaRepository;
    protected final EntityMapper<D, E> mapper;

    protected AbstractDomainService(JpaRepository<E, I> jpaRepository, EntityMapper<D, E> mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<D> findById(I id) {
        return this.jpaRepository.findById(id).map(this::enrich);
    }

    @Override
    public List<D> findAllByIds(List<I> ids) {
        return this.jpaRepository.findAllById(ids).stream().map(this::enrich).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public D save(D domain) {
        E entity = this.mapper.toEntity(domain);
        this.jpaRepository.save(entity);
        return domain;
    }

    @Override
    @Transactional
    public List<D> saveAll(List<D> domains) {
        List<E> entities = this.mapper.toEntity(domains);
        this.jpaRepository.saveAll(entities);
        return domains;
    }

    protected D enrich(E entity) {
        List<E> entities = List.of(entity);
        return this.enrich(entities).get(0);
    }

    protected List<D> enrich(List<E> entity) {
        return this.mapper.toDomain(entity);
    }
}