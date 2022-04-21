package ru.bironix.super_food.db.models.dish;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.db.models.Action;

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

    @NonNull
    @OneToOne(optional = false)
    Portion basePortion;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    List<Portion> portions;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumns({
            @JoinColumn(name = "dish_id"),
            @JoinColumn(name = "addon_id")
    })
    List<Addon> addons;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumns({
            @JoinColumn(name = "dish_id"),
            @JoinColumn(name = "inner_dish_id")
    })
    List<Dish> dishes;

    @NonNull
    Boolean deleted = false;


    //TODO подумать о централизованном подходе к удалению
    @ManyToMany(cascade = {CascadeType.MERGE})
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