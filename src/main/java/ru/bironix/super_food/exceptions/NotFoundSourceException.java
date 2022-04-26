package ru.bironix.super_food.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class NotFoundSourceException extends RuntimeException {

    private List<Integer> notFoundIds;
    private String entityName;

//    public NotFoundSourceException(String entityName) {
//        super("Запрашиваемый элемент не существует");
//        this.entityName = entityName;
//    }

    public NotFoundSourceException(List<Integer> notFoundIds, String entityName) {
        super("Запрашиваемый элемент не существует");
        this.notFoundIds = notFoundIds;
        this.entityName = entityName;
    }

    public NotFoundSourceException(Integer notFoundId, String entityName) {
        super("Запрашиваемый элемент не существует");
        this.notFoundIds = List.of(notFoundId);
        this.entityName = entityName;
    }
}
