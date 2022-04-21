package ru.bironix.super_food.db.models.person;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    @NonNull
    String email;

    @NonNull
    String password;

    @NonNull
    String name;

    @OneToMany(cascade = CascadeType.ALL)
    List<Address> addresses;
}