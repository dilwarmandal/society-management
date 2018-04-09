package com.society.util;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class HibernateUtilImpl implements HibernateUtil {

    @Autowired
    private SessionFactory sessionFactory;

    public <T> Serializable create(final T entity) {
        return sessionFactory.getCurrentSession().save(entity);
    }

    public <T> void persist(final T entity) {
        sessionFactory.getCurrentSession().persist(entity);
    }

    public <T> T update(final T entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    @Override
    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }

    public <T> void delete(final T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public <T> void delete(Serializable id, Class<T> entityClass) {
        T entity = fetchById(entityClass, id);
        delete(entity);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> fetchAll(Class<T> entityClass) {
        return sessionFactory.getCurrentSession().createQuery(" FROM " + entityClass.getName()).list();
    }

    @SuppressWarnings("rawtypes")
    public <T> List<T> fetchAll(String query) {
        return sessionFactory.getCurrentSession().createSQLQuery(query).list();
    }

    @SuppressWarnings("unchecked")
    public <T> T fetchById(Class<T> entityClass, Serializable id) {
        return sessionFactory.getCurrentSession().get(entityClass, id);
    }

    public <T> Criteria createEntityCriteria(Class<T> persistentClass) {
        return sessionFactory.getCurrentSession().createCriteria(persistentClass);
    }
}