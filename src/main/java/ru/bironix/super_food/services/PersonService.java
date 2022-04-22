package ru.bironix.super_food.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.db.UpdateMapper;
import ru.bironix.super_food.db.dao.person.AddressDao;
import ru.bironix.super_food.db.dao.person.PersonDao;
import ru.bironix.super_food.db.models.person.Address;
import ru.bironix.super_food.db.models.person.Person;

import javax.transaction.Transactional;

@Service
public class PersonService {
    @Autowired
    public PersonService(PersonDao personDao, UpdateMapper mapper, AddressDao addressDao) {
        this.personDao = personDao;
        this.mapper = mapper;
        this.addressDao = addressDao;
    }

    final PersonDao personDao;
    final AddressDao addressDao;
    final UpdateMapper mapper;


    public Person getMe(int id) {
        return personDao.findById(id).orElse(null);
    }

    public Person createPerson(Person person) {
        person.setId(null);
        return personDao.save(person);
    }

    @Transactional
    public Person updatePerson(Person updatedPerson) {
        var person = personDao.findById(updatedPerson.getId()).get();
        mapper.map(updatedPerson, person);
        return person;
    }

    @Transactional
    public Address addAddressForPerson(int id, String addressName) {
        var person = personDao.findById(id).get();
        var newAddress = addressDao.save(new Address(null, addressName, person));
        person.getAddresses().add(newAddress);
        return newAddress;
    }

    @Transactional
    public void deleteAddress(int id) {
        addressDao.deleteById(id);
    }
}
