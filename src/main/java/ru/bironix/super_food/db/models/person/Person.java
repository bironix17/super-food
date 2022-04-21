package ru.bironix.super_food.db.models.person;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.Valid;
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

    String email;
    String password;
    String name;

    @Valid
    @OneToMany(cascade = CascadeType.ALL)
    List<Address> addresses;
}