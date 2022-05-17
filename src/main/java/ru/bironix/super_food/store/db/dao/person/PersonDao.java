package ru.bironix.super_food.store.db.dao.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.store.db.models.person.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonDao extends JpaRepository<Person, Integer> {
    Optional<Person> findByEmail(String email);

    List<Person> findByAddresses_Id(Integer id);

}
