package ru.el59.springboot2.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.el59.springboot2.critery.ClientCritery;
import ru.el59.springboot2.entity.Client;
import ru.el59.springboot2.service.ClientService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ClientServiceTest {
    @Autowired
    ClientService clientService;

    @Test
    public void contextLoads() {
        List<Client> clients = clientService.findAll();
        List<Client> expected = Arrays.asList(
                new Client(new Long(0), "NAME_0","PHONE_0"),
                new Client(new Long(1), "NAME_1","PHONE_0")
        );
        assert Arrays.equals(clients.toArray(), expected.toArray());
    }

    @Test
    public void findByIdTest() {
        Long ID = new Long(1);
        Client client = clientService.findById(ID);
        assert client.getId().equals(ID);
    }

    @Test
    public void findByNameTest() {
        String NAME = "NAME_1";
        Client client = clientService.findByName(NAME);
        assert client.getName().equals(NAME);
        assert client.getId().equals(new Long(1));
    }

    @Test
    public void saveTest() {
        Long ID = new Long(1);
        Client client = clientService.findById(ID);
        String NEW_NAME = "NEW_NAME";
        client.setName(NEW_NAME);

        Client c = clientService.save(client);
        assert c.getName().equals(NEW_NAME);
    }

    @Test
    public void createTest() {
        String NEW_NAME = "NEW_NAME";
        String NEW_PHONE = "NEW_PHONE";
        Client client = new Client(null, NEW_NAME,NEW_PHONE);

        Client c = clientService.save(client);
        assert c.getName().equals(NEW_NAME);
    }

    @Test
    public void deleteTest() {
        Long ID = new Long(1);
        Client client = clientService.findById(ID);
        clientService.delete(client);
        List<Client> clients = clientService.findAll();
        assert clients.size() == 1;
    }

    @Test
    public void findByIdInTest() {
        Long[] ids = new Long[]{new Long(1), new Long(0)};
        List<Client> clients = clientService.findByIdIn(ids);
        assert clients.size() == 2;
    }

    @Test
    public void findByCriteryNameTest() {
        ClientCritery critery = new ClientCritery();
        critery.name = "name_1";
        List<Client> clients = clientService.findByCritery(critery);
        assert clients.size() == 1;
        assert clients.get(0).getName().equals("NAME_1");

        critery = new ClientCritery();
        critery.name = "name";
        clients = clientService.findByCritery(critery);
        assert clients.size() == 2;
        assert clients.get(0).getName().equals("NAME_0");
    }

    @Test
    public void findByCriteryIdsTest() {
        ClientCritery critery = new ClientCritery();
        Long ID1 = new Long(1);
        critery.ids = Arrays.asList(ID1);
        List<Client> clients = clientService.findByCritery(critery);
        assert clients.size() == 1;
        assert clients.get(0).getId().equals(ID1);

        Long ID0 = new Long(0);
        critery = new ClientCritery();
        critery.ids = Arrays.asList(ID0, ID1);
        clients = clientService.findByCritery(critery);
        assert clients.size() == 2;
        assert clients.get(0).getId().equals(ID0);
        assert clients.get(1).getId().equals(ID1);
    }

    @Test
    public void findByCriteryByIdsAndNameTest() {
        ClientCritery critery = new ClientCritery();
        Long ID1 = new Long(1);
        critery.ids = Arrays.asList(ID1);
        critery.name = "name";
        List<Client> clients = clientService.findByCritery(critery);
        assert clients.size() == 1;
        assert clients.get(0).getId().equals(ID1);
    }

    @Test
    public void findByCriteryByEmptyTest() {
        ClientCritery critery = new ClientCritery();
        List<Client> clients = clientService.findByCritery(critery);
        assert clients.size() == 2;
//        assert clients.get(0).getId().equals(ID1);
    }
}
