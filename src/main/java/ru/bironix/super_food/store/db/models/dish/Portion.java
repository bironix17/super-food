package ru.bironix.super_food.store.db.models.dish;

import lombok.*;
import ru.bironix.super_food.store.db.interfaces.ForOrderEquals;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String size;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "priceNowId", nullable = false)
    Price priceNow;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "oldPriceId")
    Price oldPrice;

    @Builder.Default
    Boolean deleted = false;

    public Portion(String size, Price priceNow, Price oldPrice) {
        this.size = size;
        this.priceNow = priceNow;
        this.oldPrice = oldPrice;
    }

    @Override
    public boolean forOrderEquals(Portion portion) {
        if (portion == null) return false;

        if (!portion.getPriceNow()
                .forOrderEquals(this.getPriceNow())) return false;
        return Objects.equals(portion.getId(), getId());
    }
}
