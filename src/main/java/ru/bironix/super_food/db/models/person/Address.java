package ru.bironix.super_food.db.models.person;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;
    String address;

    @ManyToOne
    Person person;
}
