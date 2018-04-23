package ru.el59.springboot2.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.el59.springboot2.critery.OperationCritery;
import ru.el59.springboot2.entity.Account;
import ru.el59.springboot2.entity.Operation;
import ru.el59.springboot2.service.AccountService;
import ru.el59.springboot2.service.OperationService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OperationServiceTest {
    @Autowired
    OperationService operationService;
    @Autowired
    AccountService accountService;

    @Test
    public void contextLoads() {
        List<Operation> operations = operationService.findAll();
        assert operations.size() == 2;
    }

    @Test
    public void findByIdTest() {
        Long ID = new Long(1);
        Operation operation = operationService.findById(ID);
        assert operation.getId().equals(ID);
    }


    @Test
    public void saveTest() {
        Long ID = new Long(1);
        Operation operation = operationService.findById(ID);
        BigDecimal AMOUNT = new BigDecimal("10.00");
        BigDecimal SRC_BALANCE=accountService.findById(operation.getSrcAccount().getId()).getBalance();
        BigDecimal DST_BALANCE=accountService.findById(operation.getDstAccount().getId()).getBalance();
        operation.setAmount(AMOUNT);
        Operation c = operationService.save(operation);
        assert c.getAmount().compareTo(AMOUNT) == 0;
        assert accountService.findById(operation.getSrcAccount().getId()).getBalance().compareTo(SRC_BALANCE)==0;
        assert accountService.findById(operation.getDstAccount().getId()).getBalance().compareTo(DST_BALANCE)==0;
    }

    @Test
    public void createTest() {
        Long SRC_ACCOUNT_ID=new Long(1);
        Long DST_ACCOUNT_ID=new Long(2);
        Account srcAccount= accountService.findById(SRC_ACCOUNT_ID);
        Account dstAccount= accountService.findById(DST_ACCOUNT_ID);
        BigDecimal SRC_BALANCE=srcAccount.getBalance();
        BigDecimal DST_BALANCE=dstAccount.getBalance();
        BigDecimal AMOUNT = new BigDecimal("10.00");

        Operation operation = new Operation();
        operation.setSrcAccount(srcAccount);
        operation.setDstAccount(dstAccount);
        operation.setAmount(AMOUNT);

        Operation newOperation = operationService.save(operation);

        // Проверка полей
        assert newOperation.getAmount().compareTo(AMOUNT)==0;
        assert newOperation.getId().equals(new Long(2));
        assert newOperation.getSrcAccount().getId().equals(srcAccount.getId());
        assert newOperation.getDstAccount().getId().equals(dstAccount.getId());
        // Проверка изменения балансов
        assert newOperation.getSrcAccount().getBalance().add(AMOUNT).compareTo(SRC_BALANCE)==0;
        assert newOperation.getDstAccount().getBalance().subtract(AMOUNT).compareTo(SRC_BALANCE)==0;
    }

    @Test
    public void deleteTest() {
        Long ID = new Long(1);
        Operation operation = operationService.findById(ID);
        BigDecimal AMOUNT = operation.getAmount();
        BigDecimal SRC_BALANCE=accountService.findById(operation.getSrcAccount().getId()).getBalance();
        BigDecimal DST_BALANCE=accountService.findById(operation.getDstAccount().getId()).getBalance();


        operationService.delete(operation);
        List<Operation> operations = operationService.findAll();
        assert operations.size() == 1;

        // Проверка изменения балансов
        Account srcAccount= accountService.findById(operation.getSrcAccount().getId());
        Account dstAccount= accountService.findById(operation.getDstAccount().getId());
        assert SRC_BALANCE.add(AMOUNT).compareTo(srcAccount.getBalance())==0;
        assert DST_BALANCE.subtract(AMOUNT).compareTo(dstAccount.getBalance())==0;
    }

    @Test
    public void findByIdInTest() {
        Long[] ids = new Long[]{new Long(0), new Long(1)};
        List<Operation> operations = operationService.findByIdIn(ids);
        assert operations.size() == 2;

        ids = new Long[]{new Long(1)};
        operations = operationService.findByIdIn(ids);
        assert operations.size() == 1;
    }

    @Test
    public void testGetByCriteryFromDate() throws Exception {
        OperationCritery operationCritery = new OperationCritery();
        Calendar cal = Calendar.getInstance();
        cal.set(2018, 2, 10);
        operationCritery.fromDate = cal.getTime();
        List<Operation> operations = operationService.findByCritery(operationCritery);
        assert operations.size()== 1;
        assert operations.get(0).getId().compareTo(new Long(1))==0;
    }

    @Test
    public void testGetByCriteryToDate() throws Exception {
        OperationCritery operationCritery = new OperationCritery();
        Calendar cal = Calendar.getInstance();
        cal.set(2018, 2, 9);
        operationCritery.toDate = cal.getTime();
        List<Operation> operations = operationService.findByCritery(operationCritery);
        assert operations.size()== 1;
        assert operations.get(0).getId().compareTo(new Long(0))==0;
    }

    @Test
    public void testGetByCriterySrcAccount() throws Exception {
        Long ACCOUNT_ID=new Long(1);
        OperationCritery operationCritery = new OperationCritery();
        operationCritery.srcAccountId = ACCOUNT_ID;
        List<Operation> operations = operationService.findByCritery(operationCritery);
        assert operations.size()== 1;
        assert operations.get(0).getSrcAccount().getId().compareTo(ACCOUNT_ID)==0;
    }

    @Test
    public void testGetByCriteryDstAccount() throws Exception {
        Long ACCOUNT_ID=new Long(2);
        OperationCritery operationCritery = new OperationCritery();
        operationCritery.dstAccountId = ACCOUNT_ID;
        List<Operation> operations = operationService.findByCritery(operationCritery);
        assert operations.size()== 1;
        assert operations.get(0).getDstAccount().getId().compareTo(ACCOUNT_ID)==0;
    }
}
