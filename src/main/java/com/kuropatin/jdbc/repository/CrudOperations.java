package com.kuropatin.jdbc.repository;

import java.util.List;

public interface CrudOperations<K, T> {

    List<T> findAll();

    T findOne(K id);

    T create(T entity);

    T update(K id, T entity);

    void delete(K id);
}