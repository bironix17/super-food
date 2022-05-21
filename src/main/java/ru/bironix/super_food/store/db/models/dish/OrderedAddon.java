package ru.bironix.super_food.store.db.models.dish;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderedAddon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne(cascade = CascadeType.REFRESH, optional = false)
    Addon addon;

    @OneToOne(cascade = CascadeType.REFRESH, optional = false)
    Price price;

}
