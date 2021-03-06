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

    @Column(nullable = false, length=500)
    String name;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    PicturePaths picturePaths;

    @ManyToMany(cascade = {CascadeType.REFRESH})
    @JoinColumns({
            @JoinColumn(name = "action_id"),
            @JoinColumn(name = "dish_id", unique = true)
    })
    List<Dish> dishes;

    @ManyToMany(cascade = {CascadeType.REFRESH})
    List<Portion> portions;

    public Action(Action other) {
        this.id = other.id;
        this.name = other.name;
        this.picturePaths = other.picturePaths;
        this.dishes = other.dishes;
        this.portions = other.portions;
    }
}
