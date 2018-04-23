package ru.el59.springboot2.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ru.el59.springboot2.critery.OperationCritery;
import ru.el59.springboot2.entity.Account;
import ru.el59.springboot2.entity.Operation;
import ru.el59.springboot2.entity.QOperation;
import ru.el59.springboot2.repository.AccountRepository;
import ru.el59.springboot2.repository.OperationRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("operationService")
@Transactional
public class OperationServiceImpl extends ACrudService<Operation, Long> implements OperationService {

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    CrudRepository<Operation, Long> getRepository() {
        return operationRepository;
    }

    @Override
    public List<Operation> findByIdIn(Long[] ids) {
        OperationCritery critery = new OperationCritery();
        critery.ids = Arrays.asList(ids);
        return findByCritery(critery);
    }

    @Override
    public List<Operation> findByCritery(OperationCritery critery) {
        QOperation qOperation = QOperation.operation;
        List<BooleanExpression> predicates = new ArrayList<>();
        if (critery.ids.size() > 0) {
            predicates.add(qOperation.id.in(critery.ids));
        }
        if (critery.fromDate!=null) {
            predicates.add(qOperation.ddate.goe(critery.fromDate));
        }
        if (critery.toDate!=null) {
            predicates.add(qOperation.ddate.loe(critery.toDate));
        }
        if (critery.srcAccountId!=null) {
            predicates.add(qOperation.srcAccount.id.eq(critery.srcAccountId));
        }
        if (critery.dstAccountId!=null) {
            predicates.add(qOperation.dstAccount.id.eq(critery.dstAccountId));
        }
        BooleanExpression expression = predicates.stream().reduce((predicate, accum) -> accum.and(predicate)).orElse(null);
        Iterable<Operation> iterOperations = operationRepository.findAll(expression, new Sort(Sort.Direction.ASC, "ddate"));
        return iterableToList(iterOperations);
    }

    /**
     * Корректировка балансов
     *
     * @param srcAccount -
     * @param dstAccount
     * @param amount
     */
    public void correctBalance(Account srcAccount, Account dstAccount, BigDecimal amount) {
        srcAccount = accountRepository.findById(srcAccount.getId()).orElse(null);
        dstAccount = accountRepository.findById(dstAccount.getId()).orElse(null);
        srcAccount.setBalance(srcAccount.getBalance().subtract(amount));
        dstAccount.setBalance(dstAccount.getBalance().add(amount));
    }

    @Override
    public Operation save(Operation operation) {
        if(operation.getId()!=null) {
            Operation oldOperation=findById(operation.getId());
            correctBalance(oldOperation.getDstAccount(), oldOperation.getSrcAccount(), oldOperation.getAmount());
        }
        correctBalance(operation.getSrcAccount(), operation.getDstAccount(), operation.getAmount());
        return super.save(operation);
    }

    @Override
    public void delete(Operation operation) {
        correctBalance(operation.getDstAccount(), operation.getSrcAccount(), operation.getAmount());
        super.delete(operation);
    }

    @Override
    public void delete(Long id) {
        Operation operation = findById(id);
        this.delete(operation);
    }
}
