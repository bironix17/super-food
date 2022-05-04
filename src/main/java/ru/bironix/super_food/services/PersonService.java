package ru.bironix.super_food.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.db.UpdateMapper;
import ru.bironix.super_food.db.dao.person.AddressDao;
import ru.bironix.super_food.db.dao.person.PersonDao;
import ru.bironix.super_food.db.models.person.Address;
import ru.bironix.super_food.db.models.person.Person;
import ru.bironix.super_food.exceptions.NotFoundSourceException;
import ru.bironix.super_food.exceptions.UserAlreadyExistAuthenticationException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonDao personDao;
    private final AddressDao addressDao;
    private final UpdateMapper mapper;

    @Autowired
    EntityManager entityManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonDao personDao, UpdateMapper mapper, AddressDao addressDao) {
        this.personDao = personDao;
        this.mapper = mapper;
        this.addressDao = addressDao;
    }

    public Person getByUsername(String email) {
        return personDao.findByEmail(email).orElseThrow(() -> new NotFoundSourceException(email, "Person"));
    }

    @Transactional
    public Person createPerson(Person person) {

        checkExist(person);

        person.setId(null);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personDao.saveAndFlush(person);
        entityManager.refresh(person);
        if (person.getAddresses() != null)
            person.getAddresses().forEach((a) -> a.setPerson(person)); //TODO подумать как пофиксить
        return person;
    }

    private void checkExist(Person person) {
        var personBd = getPersonByEmail(person.getEmail());
        if (personBd.isPresent())
            throw new UserAlreadyExistAuthenticationException("Пользователь с данным email уже существует");
    }

    @Transactional
    public Person updatePerson(Person updatedPerson) {
        var person = personDao.findById(updatedPerson.getId())
                .orElseThrow(() -> new NotFoundSourceException(updatedPerson.getId(), "Person"));

        if(updatedPerson.getPassword() != null)
            updatedPerson.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));

        mapper.map(updatedPerson, person);
        return person;
    }

    @Transactional
    public Address addAddressForPerson(String email, String addressName) {
        var person = personDao.findByEmail(email)
                .orElseThrow(() -> new NotFoundSourceException(email, "Person"));
        var newAddress = addressDao.save(new Address(null, addressName, person));
        person.getAddresses().add(newAddress);
        return newAddress;
    }

    @Transactional
    public void deleteAddress(int id) {
        if (!addressDao.existsById(id)) throw new NotFoundSourceException(id, "Address");
        addressDao.deleteById(id);
    }

    public Address getAddress(int id) {
        return addressDao.findById(id)
                .orElseThrow(() -> new NotFoundSourceException(id, "Address"));
    }

    public Optional<Person> getPersonByEmail(String email) {
        return personDao.findByEmail(email);
    }

    public Person getById(Integer id) {
        return personDao.findById(id).orElseThrow(() -> new NotFoundSourceException(id, "Person"));
    }
}