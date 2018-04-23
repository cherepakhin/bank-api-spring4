package ru.el59.springboot2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.el59.springboot2.controller.util.Operations;
import ru.el59.springboot2.critery.OperationCritery;
import ru.el59.springboot2.entity.Operation;
import ru.el59.springboot2.service.ACrudService;
import ru.el59.springboot2.service.OperationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/operation")
public class OperationController extends ASimpleRestController<Operation, Long> {
    public static final Logger logger = LoggerFactory.getLogger(OperationController.class);
    final static SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    OperationService operationService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Operations> list(
            @RequestParam(value = "ids", required = false) List<Long> ids,
            @RequestParam(value = "src_account_id", required = false) Long srcAccountId,
            @RequestParam(value = "dst_account_id", required = false) Long dstAccountId,
            @RequestParam(value = "from_date", required = false) String fromDate,
            @RequestParam(value = "to_date", required = false) String toDate
    ) {
        OperationCritery operationCritery = new OperationCritery();
        if (ids != null && ids.size() > 0) {
            operationCritery.ids = ids;
        }
        operationCritery.srcAccountId = srcAccountId;
        operationCritery.dstAccountId = dstAccountId;
        if (fromDate != null && !fromDate.isEmpty()) {
            Date ddate = null;
            try {
                ddate = parser.parse(fromDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return new ResponseEntity(
                        new CustomErrorType("Error Operation.fromDate " + e.getMessage()),
                        HttpStatus.NOT_FOUND);
            }
            operationCritery.fromDate = ddate;
        }
        if (toDate != null && !toDate.isEmpty()) {
            Date ddate = null;
            try {
                ddate = parser.parse(toDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return new ResponseEntity(
                        new CustomErrorType("Error Operation.toDate " + e.getMessage()),
                        HttpStatus.NOT_FOUND);
            }
            operationCritery.toDate = ddate;
        }
        List<Operation> operations = operationService.findByCritery(operationCritery);
        return new ResponseEntity<Operations>(new Operations(operations), HttpStatus.OK);
    }

    @Override
    public ACrudService<Operation, Long> getService() {
        return (ACrudService<Operation, Long>) operationService;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
