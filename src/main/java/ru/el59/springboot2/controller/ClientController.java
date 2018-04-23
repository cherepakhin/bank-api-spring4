package ru.el59.springboot2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.el59.springboot2.controller.util.Clients;
import ru.el59.springboot2.critery.ClientCritery;
import ru.el59.springboot2.entity.Client;
import ru.el59.springboot2.service.ACrudService;
import ru.el59.springboot2.service.ClientService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/client")
public class ClientController extends ASimpleRestController<Client,Long>{
    public static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Clients> list(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "ids", required = false) List<Long> ids
    ) {
//        logger.info("Param name:" + name + ";");
//        logger.info("Param ids:" + ids + ";");
        ClientCritery clientCritery = new ClientCritery();
        if (name != null && !name.isEmpty()) {
            clientCritery.name = name;
        }
        if (ids != null && ids.size() > 0) {
            clientCritery.ids = ids;
        }
        List<Client> clients = clientService.findByCritery(clientCritery);
        return new ResponseEntity<Clients>(new Clients(clients), HttpStatus.OK);
    }

    @Override
    public ACrudService<Client, Long> getService() {
        return (ACrudService<Client, Long>) clientService;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
