package ru.el59.springboot2.critery;

import java.util.ArrayList;
import java.util.List;

public class AccountCritery {
    /**
     * Список идентификаторов
     */
    public List<Long> ids = new ArrayList<Long>();
    /**
     * Имя для поиска
     */
    public String name;

    /**
     * Ид клиента
     */
    public Long clientId;
    /**
     * Имя клинта
     */
    public String clientName;

}
