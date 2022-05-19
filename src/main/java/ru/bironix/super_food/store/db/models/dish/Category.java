package ru.bironix.super_food.store.db.models.dish;

import lombok.*;
import ru.bironix.super_food.store.db.interfaces.ForOrderEquals;
import ru.bironix.super_food.store.db.interfaces.GetTotalPrice;
import ru.bironix.super_food.store.db.models.common.PicturePaths;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
    @Id
    String name;
}