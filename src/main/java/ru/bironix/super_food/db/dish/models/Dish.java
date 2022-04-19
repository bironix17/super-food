package ru.bironix.super_food.db.dish.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.db.action.models.Action;
import ru.bironix.super_food.db.generalModels.PicturePaths;

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

    @NonNull
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    PicturePaths picturePaths;

    @NonNull
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

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumns({
            @JoinColumn(name = "dish_id", unique = false),
            @JoinColumn(name = "addon_id", unique = false)
    })
    List<Addon> addons;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumns({
            @JoinColumn(name = "dish_id", unique = false),
            @JoinColumn(name = "inner_dish_id", unique = false)
    })
    List<Dish> dishes;

    @NonNull
    Boolean deleted = false;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinColumns({
            @JoinColumn(name = "dish_id", unique = false),
            @JoinColumn(name = "action_id", unique = false)
    })
    private List<Action> actions;

    @PreRemove
    private void removeActions() {
        for (var action : actions) {
            action.getDishes().remove(this);
        }
    }
}