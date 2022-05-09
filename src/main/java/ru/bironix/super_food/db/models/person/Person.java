package ru.bironix.super_food.db.models.person;

import lombok.*;
import ru.bironix.super_food.security.Role;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true)
    String email;

    String password;
    String name;

    @Enumerated(EnumType.STRING)
    Role role = Role.User;

    String phoneNumber;

    @Valid
    @OneToMany(cascade = CascadeType.ALL)
    List<Address> addresses;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Favorite> favorites;

}