package ru.bironix.super_food.db.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.db.models.dish.Dish;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    String name;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    PicturePaths picturePaths;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.MERGE})
    List<Dish> dishes;


    //TODO подумать о централизованном подходе к удалению
    @PreRemove
    private void removeDishes() {
        for (var dish : dishes) {
            dish.getActions().remove(this);
        }
    }
}
