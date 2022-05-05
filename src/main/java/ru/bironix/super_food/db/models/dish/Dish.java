package ru.bironix.super_food.db.models.dish;

import lombok.*;
import ru.bironix.super_food.db.models.Action;
import ru.bironix.super_food.db.models.PicturePaths;
import ru.bironix.super_food.interfaces.GetTotalPrice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dish implements GetTotalPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    PicturePaths picturePaths;

    @Column(nullable = false)
    String name;

    String composition;

    @Builder.Default
    Integer count = 1;

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
    List<Addon> addons = new ArrayList<>();

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

    public boolean forOrderEquals(Dish dish) {
        if (dish == null) return false;

        if (!Objects.equals(dish.getId(), getId())) return false;
        if (!dish.getBasePortion()
                .forOrderEquals(this.getBasePortion())) return false;
        if (dish.getAddons() == null) return true;

        return dish.getAddons().stream()
                .allMatch(a -> getAddons().stream()
                        .anyMatch(a1 -> a1.forOrderEquals(a)));
    }

    public List<Addon> getAddons() {
        if (addons != null) return addons;
        else return new ArrayList<>();
    }

    @Override
    public int getTotalPrice() {
        return basePortion.getPriceNow().getPrice() * count
                + addons.stream().mapToInt(Addon::getTotalPrice).sum();
    }
}