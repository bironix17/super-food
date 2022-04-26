package ru.bironix.super_food.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class DeletedDishInOrderException extends RuntimeException {

    private List<Integer> deletedIds;

    public DeletedDishInOrderException(List<Integer> deletedIds) {
        super("Удаленные блюда не могут быть в заказе");
        this.deletedIds = deletedIds;
    }
}
