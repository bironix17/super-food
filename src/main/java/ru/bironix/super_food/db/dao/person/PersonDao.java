package ru.bironix.super_food.db.dao.person;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.models.person.Person;

@Repository
public interface PersonDao extends CrudRepository<Person, Integer> {
}
