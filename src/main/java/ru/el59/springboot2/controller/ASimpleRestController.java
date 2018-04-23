package ru.el59.springboot2.controller;

import org.slf4j.Logger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.el59.springboot2.service.ACrudService;

import java.io.Serializable;

public abstract class ASimpleRestController<T, PK extends Serializable> {

    public abstract ACrudService<T, PK> getService();

    public abstract Logger getLogger();

    /**
     * Получить сущность по идентификатору
     *
     * @param id - идентификатор
     * @return - объект типа Entity
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<T> getById(@PathVariable("id") PK id) {
        getLogger().info("Fetching Client with id {}", id);
        T entity = (T) getService().findById(id);
        if (entity == null) {
            getLogger().error("Client with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Client with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<T>(entity, HttpStatus.OK);
    }

    /**
     * Создание сущности
     *
     * @param o - объект для создания
     * @return - созданная сущность
     * @throws Exception - ошибка
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public T create(@RequestBody T o) throws Exception {
        getLogger().info("Create: " + o);
        T ret = getService().save(o);
        getLogger().info("Successfully created:" + o);
        return ret;
    }

    /**
     * Обновление сущности
     *
     * @param o - объект для обновления
     * @param id - идентификатор сущности
     * @return - обновленная сущность
     * @throws Exception - ошибка
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public T update(@RequestBody T o, @PathVariable PK id) throws Exception {
        getLogger().info("Update: " + o);
        T ret = getService().save(o);
        getLogger().info("Successfully updated:" + o);
        return ret;
    }

    /**
     * Удаление сущности
     *
     * @param id - идентификатор
     * @return - пустая строка (сделать описание ошибки)
     * @throws Exception - ошибка
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable PK id) throws Exception {
        getLogger().info("Delete: " + id);
        getService().delete(id);
        getLogger().info("Successfully deleted:" + id);
        return "";
    }

}
