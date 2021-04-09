package com.google.sps;

/** DAO Interface */
public interface Dao<T> {
  public T create(T obj);

  public void update(T obj);

  public T read(Long id);

  public void delete(Long id);
}