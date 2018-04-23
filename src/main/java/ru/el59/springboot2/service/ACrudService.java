package ru.el59.springboot2.service;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class ACrudService<T,PK extends Serializable>  {

    abstract CrudRepository<T,PK> getRepository();

    public T findById(PK id) {
        T entity = getRepository().findById(id).orElse(null);
        return entity;
    }

    public T save(T o) {
        System.out.println(o);
        T ret = getRepository().save(o);
        return ret;
    }

    public void delete(T o) {
        getRepository().delete(o);
    }

    public List<T> findAll() {
        Iterable<T> iterEntities = getRepository().findAll();
        return iterableToList(iterEntities);
    }

    public void delete(PK id) {
        getRepository().deleteById(id);
    }

    public List<T> iterableToList(Iterable<T> iterEntities) {
        List<T> entities = StreamSupport.stream(iterEntities.spliterator(), false).collect(Collectors.toList());
        if(entities==null) {
            entities= new ArrayList<T>();
        }
        return entities;
    }
}
