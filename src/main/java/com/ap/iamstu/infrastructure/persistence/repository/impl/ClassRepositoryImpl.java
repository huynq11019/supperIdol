package com.ap.iamstu.infrastructure.persistence.repository.impl;

import com.ap.iamstu.infrastructure.persistence.entity.ClassEntity;
import com.ap.iamstu.infrastructure.persistence.query.ClassSearchQuery;
import com.ap.iamstu.infrastructure.persistence.repository.custom.ClassRepositoryCustom;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassRepositoryImpl implements ClassRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ClassEntity> searchClass(ClassSearchQuery querySearch) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder hql = new StringBuilder("SELECT c FROM ClassEntity c WHERE 1=1 ");
        hql.append(createSearchQuery(querySearch, values));
        hql.append(" AND c.deleted = false");

        hql.append(createOrderQuery(querySearch.getSortBy()));
        Query query = entityManager.createQuery(hql.toString(), ClassEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((querySearch.getPageIndex() - 1) * querySearch.getPageSize());
        query.setMaxResults(querySearch.getPageSize());
        return query.getResultList();
    }

    @Override
    public Long countClasses(ClassSearchQuery searchQuery) {
        Map<String, Object> values = new HashMap<>();
        StringBuilder hql = new StringBuilder("SELECT count(c) FROM ClassEntity c WHERE 1=1");
        hql.append(createSearchQuery(searchQuery, values));
        hql.append(" AND c.deleted = false");
        Query query = entityManager.createQuery(hql.toString(), Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }

    private StringBuilder createSearchQuery(ClassSearchQuery querySearch,Map<String, Object> values ) {
        StringBuilder hql = new StringBuilder(" ");
        if (StringUtils.hasLength(querySearch.getKeyword())) {
            hql.append(" AND c.name LIKE :name");
            values.put("name", "%" + querySearch.getKeyword() + "%");
        }
        return hql;
    }


    public StringBuilder createOrderQuery(String sortBy) {
        StringBuilder hql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            hql.append(" order by U.").append(sortBy.replace(".", " "));
        }
        return hql;
    }
}
