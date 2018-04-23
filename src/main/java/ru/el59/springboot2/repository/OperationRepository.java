package ru.el59.springboot2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.el59.springboot2.entity.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long>, QuerydslPredicateExecutor<Operation> {
}
