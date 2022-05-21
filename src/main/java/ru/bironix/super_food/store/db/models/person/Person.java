package ru.bironix.super_food.store.db.models.person;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

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

    @Column(unique = true, nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String password;

    String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;

    @Column(nullable = false)
    Boolean banned;

    @Valid
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    List<Address> addresses;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Favorite> favorites;

    public Person(Person other) {
        this.id = other.id;
        this.password = other.password;
        this.name = other.name;
        this.role = other.role;
        this.phoneNumber = other.phoneNumber;
        this.banned = other.banned;
        this.addresses = other.addresses;
        this.favorites = other.favorites;
    }

    @PrePersist
    void prePersist() {
        role = defaultIfNull(role, Role.ROLE_CLIENT);
        banned = defaultIfNull(banned, false);
    }
}