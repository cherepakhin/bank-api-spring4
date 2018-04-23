package ru.el59.springboot2.service;

import ru.el59.springboot2.critery.ClientCritery;
import ru.el59.springboot2.entity.Client;

import java.io.Serializable;
import java.util.List;

public interface ICrudService<T,PK extends Serializable> {
    T findById(PK id);
    T save(T o);
    void delete(T o);
}
