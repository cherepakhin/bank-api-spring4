package ru.el59.springboot2.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import ru.el59.springboot2.entity.Client;
import ru.el59.springboot2.service.ACrudService;

import javax.transaction.Transactional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Transactional
public class ClientControllerUnitTest {

    Long ID = new Long(2);
    String NAME = "NAME";
    Client client = new Client();
    ACrudService clientService;

    {
        client.setId(ID);
        client.setName(NAME);
    }

    ClientController spyClientController;

    @Before
    public void setup() throws Exception {
        ClientController clientController = new ClientController();
        spyClientController = Mockito.spy(clientController);
        clientService = mock(ACrudService.class);
        when(spyClientController.getService()).thenReturn(clientService);
    }

    @Test
    public void readSingleCLient() throws Exception {
        when(clientService.findById(ID)).thenReturn(client);
        ResponseEntity<Client> response = spyClientController.getById(ID);
        assert response.getStatusCodeValue()==200;
        assert response.getBody().equals(client);
    }

    @Test
    public void readNoneCLient() throws Exception {
        Long ID_NONE = new Long(100);
        when(clientService.findById(ID_NONE)).thenReturn(null);
        ResponseEntity<Client> response = spyClientController.getById(ID_NONE);
        System.out.println(response);
        assert response.getStatusCodeValue()==404;
    }

}
