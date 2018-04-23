package ru.el59.springboot2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.el59.springboot2.entity.Client;
import ru.el59.springboot2.service.ClientService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class Springboot2ApplicationTests {

    @Test
    public void contextLoads() {
    }
}
