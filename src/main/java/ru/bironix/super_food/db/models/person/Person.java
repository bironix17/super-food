package ru.bironix.super_food.db.models.person;

import lombok.*;
import ru.bironix.super_food.security.Role;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    @Column(unique = true)
    String email;

    String PhoneNumber;

    String password;
    String name;

    @Enumerated(EnumType.STRING)
    Role role = Role.User;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")//TODO зачем здесь mappedBy
    List<Address> addresses;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Favorite> favorites;

    public boolean forOrderEquals(Person person) {
        if (person == null) return false;

        return Objects.equals(person.getId(), getId());
    }
}