package ru.bironix.super_food.store.db.models.dish;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DishCount{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne(cascade = CascadeType.REFRESH)
    Dish dish;
    @OneToOne(cascade = CascadeType.REFRESH)
    Portion portion;
    @OneToOne(cascade = CascadeType.REFRESH)
    Price dishPrice;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "dish_id"),
            @JoinColumn(name = "addonPrice_id")
    })
    List<AddonPrice> addonsPrices;

    Integer count;

    @PrePersist
    void prePersist() {
        count = defaultIfNull(count, 1);
    }
}
