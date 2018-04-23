package ru.el59.springboot2.controller.util;

import ru.el59.springboot2.entity.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Accounts implements Serializable {
    private List<Account> accounts = new ArrayList<Account>();

    public Accounts() {

    }

    public Accounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
