package ru.bironix.super_food.exceptions;

public class NotFoundSource extends RuntimeException {
    public NotFoundSource() {
        super("Запрашиваемый элемент не существует");
    }
}
