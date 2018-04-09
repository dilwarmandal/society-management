package com.society.util;

import org.hibernate.Criteria;

import java.io.Serializable;
import java.util.List;

public interface HibernateUtil {
    <T> Serializable create(final T entity);

    <T> void persist(final T entity);

    <T> T update(final T entity);

    void flush();

    <T> void delete(final T entity);

    <T> void delete(Serializable id, Class<T> entityClass);

    <T> List<T> fetchAll(Class<T> entityClass);

    <T> List fetchAll(String query);

    <T> T fetchById(Class<T> entityClass, Serializable id);

    <T> Criteria createEntityCriteria(Class<T> persistentClass);
}
