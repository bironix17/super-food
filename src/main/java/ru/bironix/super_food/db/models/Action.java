package ru.bironix.super_food.db.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.db.models.dish.Dish;
import ru.bironix.super_food.db.models.dish.PicturePaths;

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

    @NonNull
    String name;

    @NonNull
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
