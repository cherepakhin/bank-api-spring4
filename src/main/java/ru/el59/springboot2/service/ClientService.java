package ru.el59.springboot2.service;

import org.springframework.data.repository.CrudRepository;
import ru.el59.springboot2.critery.ClientCritery;
import ru.el59.springboot2.entity.Client;

import java.util.List;

public interface ClientService extends ICrudService<Client,Long> {
    List<Client> findAll();
    Client findByName(String name);
    List<Client> findByIdIn(Long[] ids);
    List<Client> findByCritery(ClientCritery critery);
}
