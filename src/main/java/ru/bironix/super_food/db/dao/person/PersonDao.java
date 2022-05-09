package ru.bironix.super_food.db.dao.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.models.person.Person;

import java.util.Optional;

@Repository
public interface PersonDao extends JpaRepository<Person, Integer> {
    Optional<Person> findByEmail(String email);
}
