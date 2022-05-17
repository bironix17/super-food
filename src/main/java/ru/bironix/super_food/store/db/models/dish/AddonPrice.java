package ru.bironix.super_food.store.db.models.dish;

import lombok.*;
import ru.bironix.super_food.store.db.interfaces.GetTotalPrice;

import javax.persistence.*;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AddonPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    @OneToOne(cascade = CascadeType.REFRESH, optional = true)
    Addon addon;

    @OneToOne(cascade = CascadeType.REFRESH, optional = true)
    Price price;

}
