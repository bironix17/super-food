package ru.bironix.super_food.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.db.dao.person.AddressDao;
import ru.bironix.super_food.db.dao.person.FavoritesDao;
import ru.bironix.super_food.db.dao.person.PersonDao;
import ru.bironix.super_food.db.models.person.Address;
import ru.bironix.super_food.db.models.person.Favorite;
import ru.bironix.super_food.db.models.person.Person;
import ru.bironix.super_food.db.utils.UpdateMapper;
import ru.bironix.super_food.exceptions.ApiException;
import ru.bironix.super_food.exceptions.NotFoundSourceException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonDao personDao;
    private final AddressDao addressDao;
    private final FavoritesDao favoritesDao;
    private final UpdateMapper mapper;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonDao personDao,
                         AddressDao addressDao,
                         FavoritesDao favoritesDao,
                         UpdateMapper mapper,
                         EntityManager entityManager,
                         PasswordEncoder passwordEncoder) {
        this.personDao = personDao;
        this.addressDao = addressDao;
        this.favoritesDao = favoritesDao;
        this.mapper = mapper;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    public Person getPersonByUsername(String email) {
        return personDao.findByEmail(email).orElseThrow(() ->
                new NotFoundSourceException("Person"));
    }

    @Transactional
    public Person createPerson(Person person) {

        checkPersonExist(person);

        person.setId(null);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personDao.saveAndFlush(person);
        entityManager.refresh(person);
        return person;
    }

    private void checkPersonExist(Person person) {
        var personBd = getPersonByEmail(person.getEmail());
        if (personBd.isPresent())
            throw new ApiException(ApiError.USER_ALREADY_EXIST);
    }

    @Transactional
    public Person updatePerson(Person updatedPerson) {
        var person = personDao.findById(updatedPerson.getId())
                .orElseThrow(() -> new NotFoundSourceException(updatedPerson.getId(), "Person"));

        if (updatedPerson.getPassword() != null)
            updatedPerson.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));

        mapper.map(updatedPerson, person);
        return person;
    }

    @Transactional
    public Address addAddressForPerson(Integer personId, String addressName) {
        var person = personDao.findById(personId)
                .orElseThrow(() -> new NotFoundSourceException("Person"));
        var newAddress = addressDao.save(new Address(null, addressName));
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

    public Person getPersonById(Integer id) {
        return personDao.findById(id).orElseThrow(() -> new NotFoundSourceException(id, "Person"));
    }

    public List<Favorite> getFavoritesForPerson(Integer personId) {
        var person = personDao.findById(personId)
                .orElseThrow(() -> new NotFoundSourceException("Person"));
        return person.getFavorites();
    }

    @Transactional
    public Favorite addFavoritesForPerson(Integer id, Integer dishId) {
        var person = personDao.findById(id)
                .orElseThrow(() -> new NotFoundSourceException("Person"));

        if (person.getFavorites().stream()
                .anyMatch(f -> f.getDishId() == dishId))
            throw new ApiException(ApiError.ENTITY_ALREADY_EXISTS);

        var newFavorite = favoritesDao.save(new Favorite(null, dishId));
        person.getFavorites().add(newFavorite);
        return newFavorite;
    }

    @Transactional
    public void deleteFavoritesForPerson(Integer personId, int dishId) {
        var person = personDao.findById(personId)
                .orElseThrow(() -> new NotFoundSourceException("Person"));

        var favorite = person.getFavorites().stream()
                .filter(f -> f.getDishId() == dishId)
                .findAny()
                .orElseThrow(()-> new NotFoundSourceException(dishId,"Favorite"));

        person.getFavorites().remove(favorite);
        personDao.save(person);
    }

    @Transactional
    public void deletePerson(int id) {
        personDao.deleteById(id);
    }
}