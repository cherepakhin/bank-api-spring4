package ru.el59.springboot2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import ru.el59.springboot2.entity.Client;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@WebAppConfiguration
@AutoConfigureMockMvc
public class ClientControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MediaType textContentType = new MediaType(MediaType.TEXT_PLAIN.getType(),
            MediaType.TEXT_PLAIN.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

//    @Autowired
//    private WebApplicationContext webApplicationContext;

//    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Before
    public void setup() throws Exception {
//        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void readSingleCLient() throws Exception {
        Long ID = new Long(0);
        mockMvc.perform(get("/api/client/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(ID.intValue())))
                .andExpect(jsonPath("$.name", is("NAME_0")))
                .andExpect(jsonPath("$.phone", is("PHONE_0")))
        ;
    }

    @Test
    public void readListCLientResult() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/client/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.clients[0].id", is(0)))
                .andExpect(jsonPath("$.clients", hasSize(2)));
    }

    @Test
    public void createClient() throws Exception {
        Client newClient = new Client();
        String NEW_NAME = "NEW_NAME";
        String NEW_PHONE = "NEW_PHONE";
        newClient.setName(NEW_NAME);
        newClient.setPhone(NEW_PHONE);
        String clientJson = json(newClient);

        this.mockMvc.perform(post("/api/client/")
                .contentType(contentType)
                .content(clientJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is(NEW_NAME)))
                .andExpect(jsonPath("$.phone", is(NEW_PHONE)));
    }

    @Test
    public void updateClient() throws Exception {
        Long ID = new Long(0);
        String NEW_NAME = "NEW_NAME";
        String NEW_PHONE = "NEW_PHONE";

        Client client = new Client();
        client.setId(ID);
        client.setName(NEW_NAME);
        client.setPhone(NEW_PHONE);

        String clientJson = json(client);

        this.mockMvc.perform(post("/api/client/"+ID.intValue())
                .contentType(contentType)
                .content(clientJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID.intValue())))
                .andExpect(jsonPath("$.name", is(NEW_NAME)))
                .andExpect(jsonPath("$.phone", is(NEW_PHONE)));
    }

    @Test
    public void deleteCLient() throws Exception {
        Long ID = new Long(0);
        mockMvc.perform(delete("/api/client/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(textContentType))
        ;
    }

    protected String json(Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }
}
