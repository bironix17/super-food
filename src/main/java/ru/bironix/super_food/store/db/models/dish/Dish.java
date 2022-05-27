package ru.bironix.super_food.store.db.models.dish;

import lombok.*;
import ru.bironix.super_food.dtos.interfaces.CategoryType;
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
public class Dish implements GetTotalPrice, ForOrderEquals<Dish> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    PicturePaths picturePaths;

    @Column(nullable = false)
    String name;

    @Column(length=500)
    String composition;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    Category category;

    @Column(length=500)
    String description;
    @Column(length=500)
    String allergens;
    Double energyValue;
    Double protein;
    Double fat;
    Double carbohydrates;

    @OneToOne(optional = false)
    Portion basePortion;

    @OneToMany(cascade = CascadeType.ALL)
    List<Portion> portions;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinColumns({
            @JoinColumn(name = "dish_id"),
            @JoinColumn(name = "addon_id")
    })
    List<Addon> addons;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinColumns({
            @JoinColumn(name = "dish_id"),
            @JoinColumn(name = "inner_dish_id")
    })
    List<Dish> dishes;

    Boolean deleted;

    public Dish(Dish other) {
        this.id = other.id;
        this.picturePaths = other.picturePaths;
        this.name = other.name;
        this.composition = other.composition;
        this.category = other.category;
        this.description = other.description;
        this.allergens = other.allergens;
        this.energyValue = other.energyValue;
        this.protein = other.protein;
        this.fat = other.fat;
        this.carbohydrates = other.carbohydrates;
        this.basePortion = other.basePortion;
        this.portions = other.portions;
        this.addons = other.addons;
        this.dishes = other.dishes;
        this.deleted = other.deleted;
    }

    @PrePersist
    void prePersist() {
        deleted = defaultIfNull(deleted, false);
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

    @Override
    public boolean forOrderEquals(Dish dish) {
        if (dish == null) return false;

        if (!Objects.equals(dish.getId(), getId())) return false;

        if (this.getPortions().stream()
                .noneMatch(p -> p.forOrderEquals(dish.getBasePortion()))) {
            return false;
        }

        if (dish.getAddons() == null) return true;

        return dish.getAddons().stream()
                .allMatch(a -> getAddons().stream()
                        .anyMatch(a1 -> a1.forOrderEquals(a)));
    }
}