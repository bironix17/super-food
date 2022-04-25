package ru.bironix.super_food.db.models.dish;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.db.models.Action;
import ru.bironix.super_food.db.models.PicturePaths;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    PicturePaths picturePaths;

    @Column(nullable = false)
    String name;

    String composition;

    @Enumerated(EnumType.STRING)
    CategoryType category;

    String description;
    String allergens;

    @OneToOne(optional = false)
    Portion basePortion;

    @OneToMany(cascade = CascadeType.ALL)
    List<Portion> portions;

    @ManyToMany()
    @JoinColumns({
            @JoinColumn(name = "dish_id"),
            @JoinColumn(name = "addon_id")
    })
    List<Addon> addons;

    @ManyToMany()
    @JoinColumns({
            @JoinColumn(name = "dish_id"),
            @JoinColumn(name = "inner_dish_id")
    })
    List<Dish> dishes;

    Boolean deleted = false;

    //TODO подумать о централизованном подходе к удалению
    @ManyToMany()
    @JoinColumns({
            @JoinColumn(name = "dish_id"),
            @JoinColumn(name = "action_id")
    })
    private List<Action> actions;


    @PreRemove
    private void removeActions() {
        for (var action : actions) {
            action.getDishes().remove(this);
        }
    }
}