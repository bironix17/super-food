package ru.bironix.super_food.store.db.models.dish;

import lombok.*;
import ru.bironix.super_food.store.db.interfaces.ForOrderEquals;
import ru.bironix.super_food.store.db.interfaces.GetTotalPrice;
import ru.bironix.super_food.store.db.models.common.PicturePaths;

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
public class Dish implements GetTotalPrice, ForOrderEquals<Dish> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinColumns({
            @JoinColumn(name = "dish_id"),
            @JoinColumn(name = "addon_id")
    })
    List<Addon> addons = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinColumns({
            @JoinColumn(name = "dish_id"),
            @JoinColumn(name = "inner_dish_id")
    })
    List<Dish> dishes;

    @Builder.Default
    Boolean deleted = false;

    @Override
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
        return basePortion.getPriceNow().getPrice()
                + addons.stream().mapToInt(Addon::getTotalPrice).sum();
    }

    public Dish(Dish other) {
        this.id = other.id;
        this.picturePaths = other.picturePaths;
        this.name = other.name;
        this.composition = other.composition;
        this.category = other.category;
        this.description = other.description;
        this.allergens = other.allergens;
        this.basePortion = other.basePortion;
        this.portions = other.portions;
        this.addons = other.addons;
        this.dishes = other.dishes;
        this.deleted = other.deleted;
    }
}