package ru.el59.springboot2.critery;

import java.util.ArrayList;
import java.util.List;

public class ClientCritery {
    /**
     * Список идентификаторов
     */
    public List<Long> ids = new ArrayList<Long>();
    /**
     * Имя для поиска
     */
    public String name;

    public ClientCritery() {
    }

    public ClientCritery(List<Long> ids, String name) {
        if(ids!=null) {
            this.ids = ids;
        }
        this.name = name;
    }
}
