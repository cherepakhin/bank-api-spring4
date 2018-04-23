package ru.el59.springboot2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import ru.el59.springboot2.entity.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long>,QuerydslPredicateExecutor<Client> {
}
