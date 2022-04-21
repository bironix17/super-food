package ru.bironix.super_food.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.Utils;
import ru.bironix.super_food.db.dao.person.PersonDao;
import ru.bironix.super_food.db.models.person.Person;
import ru.bironix.super_food.dtos.PersonDto;

@Service
public class PersonService {

    @Autowired
    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    final PersonDao personDao;

    public Person getMe(int id) {
        return personDao.findById(id).orElse(null);
    }

    public Person createPerson(Person person) {
        return personDao.save(person);
    }
}
