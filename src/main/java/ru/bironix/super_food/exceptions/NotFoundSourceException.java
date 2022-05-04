package ru.bironix.super_food.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class NotFoundSourceException extends RuntimeException {

    private List<Object> notFoundEntities;
    private String entityName;

    public NotFoundSourceException(List<Object> notFoundEntities, String entityName) {
        super("Запрашиваемый элемент не существует");
        this.notFoundEntities = notFoundEntities;
        this.entityName = entityName;
    }

    public NotFoundSourceException(Object notFoundEntity, String entityName) {
        super("Запрашиваемый элемент не существует");
        this.notFoundEntities = List.of(notFoundEntity);
        this.entityName = entityName;
    }
}
