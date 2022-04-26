package ru.bironix.super_food.db.models.person;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.db.models.dish.Portion;

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

    String email;
    String password;
    String name;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    List<Address> addresses;

    public boolean forOrderEquals(Person person) {
        if (person == null) return false;

        return Objects.equals(person.getId(), getId());
    }
}