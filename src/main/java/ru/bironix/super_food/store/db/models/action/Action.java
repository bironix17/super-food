package ru.bironix.super_food.store.db.models.action;

import lombok.*;
import ru.bironix.super_food.store.db.models.common.PicturePaths;
import ru.bironix.super_food.store.db.models.dish.Dish;
import ru.bironix.super_food.store.db.models.dish.Portion;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    PicturePaths picturePaths;

    @ManyToMany(cascade = {CascadeType.REFRESH})
    List<Dish> dishes;

    @ManyToMany(cascade = {CascadeType.REFRESH})
    List<Portion> portions;
}
