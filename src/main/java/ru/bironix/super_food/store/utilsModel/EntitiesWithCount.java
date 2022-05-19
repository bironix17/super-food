package ru.bironix.super_food.store.utilsModel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EntitiesWithCount<T> {
    List<T> entities;
    Long count;
}
