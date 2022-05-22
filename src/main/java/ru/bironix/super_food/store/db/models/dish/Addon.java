package ru.bironix.super_food.store.db.models.dish;

import lombok.*;
import ru.bironix.super_food.store.db.interfaces.ForOrderEquals;
import ru.bironix.super_food.store.db.interfaces.GetTotalPrice;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Addon implements GetTotalPrice, ForOrderEquals<Addon> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String picturePath;

    @Column(nullable = false)
    Boolean deleted;

    @OneToOne(optional = false)
    Price priceNow;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    List<Price> prices;


    public Addon(Addon other) {
        this.id = other.id;
        this.name = other.name;
        this.picturePath = other.picturePath;
        this.deleted = other.deleted;
        this.priceNow = other.priceNow;
        this.prices = other.prices;
    }

    @PrePersist
    void prePersist() {
        deleted = defaultIfNull(deleted, false);
    }

    @Override
    public boolean forOrderEquals(Addon addon) {
        if (addon == null) return false;
        return Objects.equals(addon.getId(), getId());
    }

    @Override
    public int getTotalPrice() {
        return priceNow.getPrice();
    }

}