package ru.bironix.super_food.db.models.dish;

import lombok.*;
import ru.bironix.super_food.db.interfaces.ForOrderEquals;
import ru.bironix.super_food.db.interfaces.GetTotalPrice;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Addon implements GetTotalPrice, ForOrderEquals<Addon> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String picturePath;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "priceId", nullable = false)
    Price price;

    @Override
    public boolean forOrderEquals(Addon addon) {
        if (addon == null) return false;
        return Objects.equals(addon.getId(), getId());
    }

    @Override
    public int getTotalPrice() {
        return price.getPrice();
    }
}