package ru.el59.springboot2.service;

import org.springframework.data.repository.CrudRepository;
import ru.el59.springboot2.critery.AccountCritery;
import ru.el59.springboot2.entity.Account;

import java.util.List;

public interface AccountService  extends ICrudService<Account,Long>{
    List<Account> findAll();
    Account findByName(String name);
    List<Account> findByIdIn(Long[] ids);
    List<Account> findByCritery(AccountCritery critery);
}
