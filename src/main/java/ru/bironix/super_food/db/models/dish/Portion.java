package ru.bironix.super_food.db.models.dish;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@ToString

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Portion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    String size;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "priceNowId", nullable = false)
    Price priceNow;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "oldPriceId")
    Price oldPrice;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Portion portion = (Portion) o;
        return Objects.equals(id, portion.id) && Objects.equals(size, portion.size) && priceNow.equals(portion.priceNow) && Objects.equals(oldPrice, portion.oldPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, priceNow, oldPrice);
    }
}
