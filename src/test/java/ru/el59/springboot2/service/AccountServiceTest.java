package ru.el59.springboot2.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.el59.springboot2.critery.AccountCritery;
import ru.el59.springboot2.entity.Account;
import ru.el59.springboot2.entity.Client;
import ru.el59.springboot2.service.AccountService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountServiceTest {
    @Autowired
    AccountService accountService;

    @Test
    public void contextLoads() {
        List<Account> accounts = accountService.findAll();
        assert accounts.size() == 3;
    }

    @Test
    public void findByIdTest() {
        Long ID = new Long(1);
        Account account = accountService.findById(ID);
        assert account.getId().equals(ID);
    }

    @Test
    public void findByNameTest() {
        String NAME = "ACCOUNT_0_1";
        Account account = accountService.findByName(NAME);
        assert account.getName().equals(NAME);
        assert account.getId().equals(new Long(1));
    }

    @Test
    public void saveTest() {
        Long ID = new Long(2);
        Account account = accountService.findById(ID);
        String NEW_NAME = "NEW_NAME";
        account.setName(NEW_NAME);

        Account c = accountService.save(account);
        assert c.getName().equals(NEW_NAME);
    }

    @Test
    public void createTest() {
        String NEW_NAME = "NEW_ACCOUNT";
        Account account = new Account();
        account.setName(NEW_NAME);

        Client client = new Client();
        client.setId(new Long(1));
        account.setClient(client);

        BigDecimal BALANCE = new BigDecimal("10.00");
        account.setBalance(BALANCE);

        Account c = accountService.save(account);
        assert c.getName().equals(NEW_NAME);
        assert c.getId().equals(new Long(3));
        assert c.getBalance().compareTo(BALANCE) == 0;
    }

    @Test
    public void deleteTest() {
        Long ID = new Long(1);
        Account account = accountService.findById(ID);
        accountService.delete(account);
        List<Account> accounts = accountService.findAll();
        assert accounts.size() == 2;
    }

    @Test
    public void findByIdInTest() {
        Long[] ids = new Long[]{new Long(0), new Long(1)};
        List<Account> accounts = accountService.findByIdIn(ids);
        assert accounts.size() == 2;
    }

    @Test
    public void findByCriteryNameTest() {
        AccountCritery critery = new AccountCritery();
        critery.name = "account_0_1";
        List<Account> accounts = accountService.findByCritery(critery);
        assert accounts.size() == 1;
        assert accounts.get(0).getName().equals("ACCOUNT_0_1");

        critery = new AccountCritery();
        critery.name = "account_0";
        accounts = accountService.findByCritery(critery);
        assert accounts.size() == 2;
        assert accounts.get(0).getName().equals("ACCOUNT_0_0");
        assert accounts.get(1).getName().equals("ACCOUNT_0_1");
    }

    @Test
    public void findByCriteryIdsTest() {
        AccountCritery critery = new AccountCritery();
        Long ID1 = new Long(1);
        critery.ids = Arrays.asList(ID1);
        List<Account> accounts = accountService.findByCritery(critery);
        assert accounts.size() == 1;
        assert accounts.get(0).getId().equals(ID1);

        Long ID0 = new Long(0);
        critery = new AccountCritery();
        critery.ids = Arrays.asList(ID0, ID1);
        accounts = accountService.findByCritery(critery);
        assert accounts.size() == 2;
        assert accounts.get(0).getId().equals(ID0);
        assert accounts.get(1).getId().equals(ID1);
    }

    @Test
    public void findByCriteryByIdsAndNameTest() {
        AccountCritery critery = new AccountCritery();
        Long ID1 = new Long(1);
        critery.ids = Arrays.asList(ID1);
        critery.name = "account";
        List<Account> accounts = accountService.findByCritery(critery);
        assert accounts.size() == 1;
        assert accounts.get(0).getId().equals(ID1);
    }

    @Test
    public void findByCriteryByEmptyTest() {
        AccountCritery critery = new AccountCritery();
        List<Account> accounts = accountService.findByCritery(critery);
        assert accounts.size() == 3;
    }

    @Test
    public void findByCriteryByClientIdTest() {
        AccountCritery critery = new AccountCritery();
        Long CLIENT_ID = new Long(0);
        critery.clientId = CLIENT_ID;
        List<Account> accounts = accountService.findByCritery(critery);
        assert accounts.size() == 2;
        accounts.stream().forEach(c -> {
            assert c.getClient().getId().equals(CLIENT_ID);
        });
    }

    @Test
    public void findByCriteryByClientNameTest() {
        AccountCritery critery = new AccountCritery();
        String CLIENT_NAME = "NAME_0";
        critery.clientName= CLIENT_NAME;
        List<Account> accounts = accountService.findByCritery(critery);
        assert accounts.size() == 2;
        accounts.stream().forEach(c -> {
            assert c.getClient().getName().equals(CLIENT_NAME);
        });
    }
}
