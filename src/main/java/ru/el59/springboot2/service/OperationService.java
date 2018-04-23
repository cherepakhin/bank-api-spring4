package ru.el59.springboot2.service;

import org.springframework.data.repository.CrudRepository;
import ru.el59.springboot2.critery.OperationCritery;
import ru.el59.springboot2.entity.Operation;

import java.util.List;

public interface OperationService extends ICrudService<Operation,Long> {
    List<Operation> findAll();
    List<Operation> findByIdIn(Long[] ids);
    List<Operation> findByCritery(OperationCritery critery);
}
