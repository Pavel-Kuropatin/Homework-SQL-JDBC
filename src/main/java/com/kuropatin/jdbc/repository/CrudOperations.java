package com.kuropatin.jdbc.repository;

import java.util.List;

public interface CrudOperations<K, T> {

    List<T> findAll();

    T findOne(K id);

    T save(T entity);

    T update();

    void delete(K id);
}