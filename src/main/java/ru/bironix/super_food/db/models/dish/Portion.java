package ru.bironix.super_food.db.models.dish;

import lombok.*;
import ru.bironix.super_food.db.interfaces.ForOrderEquals;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Portion implements ForOrderEquals<Portion> {

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

    public Portion(String size, Price priceNow, Price oldPrice) {
        this.size = size;
        this.priceNow = priceNow;
        this.oldPrice = oldPrice;
    }

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


    @Override
    public boolean forOrderEquals(Portion portion) {
        if (portion == null) return false;

        if (!portion.getPriceNow()
                .forOrderEquals(this.getPriceNow())) return false;
        return Objects.equals(portion.getId(), getId());
    }
}
