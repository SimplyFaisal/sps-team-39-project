package com.google.sps;

/** DAO Interface */
public interface Dao<T> {
    public void create(T obj);

    public void update(T obj);

    public T read(String id);

    public void delete(String id);
}