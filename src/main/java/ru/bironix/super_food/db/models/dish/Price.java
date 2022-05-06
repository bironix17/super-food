package ru.bironix.super_food.db.models.dish;

import lombok.*;
import ru.bironix.super_food.db.interfaces.ForOrderEquals;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Price implements ForOrderEquals<Price> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    @Column(nullable = false)
    Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(id, price1.id) && price.equals(price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price);
    }


    @Override
    public boolean forOrderEquals(Price price) {
        if (price == null) return false;
        return Objects.equals(price.getId(), getId());
    }
}