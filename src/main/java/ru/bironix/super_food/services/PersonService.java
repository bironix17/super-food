package ru.bironix.super_food.services;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.exceptions.ApiException;
import ru.bironix.super_food.exceptions.NotFoundSourceException;
import ru.bironix.super_food.store.UpdateMapper;
import ru.bironix.super_food.store.db.dao.person.AddressDao;
import ru.bironix.super_food.store.db.dao.person.FavoritesDao;
import ru.bironix.super_food.store.db.dao.person.PersonDao;
import ru.bironix.super_food.store.db.models.dish.Dish;
import ru.bironix.super_food.store.db.models.person.Address;
import ru.bironix.super_food.store.db.models.person.Favorite;
import ru.bironix.super_food.store.db.models.person.Person;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toSet;

@Service
public class PersonService {

    private final PersonDao personDao;
    private final RefreshTokenService refreshTokenService;
    private final AddressDao addressDao;
    private final FavoritesDao favoritesDao;
    private final UpdateMapper mapper;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final DishService dishService;

    @Autowired
    public PersonService(PersonDao personDao,
                         AddressDao addressDao,
                         FavoritesDao favoritesDao,
                         UpdateMapper mapper,
                         RefreshTokenService refreshTokenService,
                         EntityManager entityManager,
                         PasswordEncoder passwordEncoder,
                         DishService dishService) {
        this.personDao = personDao;
        this.addressDao = addressDao;
        this.favoritesDao = favoritesDao;
        this.mapper = mapper;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
        this.dishService = dishService;
        this.refreshTokenService = refreshTokenService;
    }

    public Person getPersonByUsername(String email) {
        return personDao.findByEmail(email).orElseThrow(() ->
                new NotFoundSourceException("Person"));
    }

    @Transactional
    public Person createPerson(Person person) {

        var newPerson = new Person(person);
        checkPersonExist(newPerson);

        newPerson.setId(null);
        newPerson.setPassword(passwordEncoder.encode(newPerson.getPassword()));
        personDao.saveAndFlush(newPerson);
        entityManager.refresh(newPerson);
        return newPerson;
    }

    private void checkPersonExist(Person person) {
        var personBd = getPersonByEmail(person.getEmail());
        if (personBd.isPresent())
            throw new ApiException(ApiError.USER_ALREADY_EXIST);
    }

    @Transactional
    public Person updatePerson(Person updatingPerson) {
        var person = personDao.findById(updatingPerson.getId())
                .orElseThrow(() -> new NotFoundSourceException(updatingPerson.getId(), "Person"));

        if (updatingPerson.getPassword() != null)
            updatingPerson.setPassword(passwordEncoder.encode(updatingPerson.getPassword()));

        if (CollectionUtils.isNotEmpty(updatingPerson.getAddresses())) {
            updatingPerson.getAddresses().forEach(a -> {
                if (a.getId() == null)
                    addressDao.save(a);
            });
        }

        mapper.map(updatingPerson, person);
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
        personDao.findByAddresses_Id(id).forEach(p -> p.getAddresses().removeIf(a -> a.getId().equals(id)));
        addressDao.deleteById(id);
    }

    public Address getAddress(int id) {
        return addressDao.findById(id)
                .orElseThrow(() -> new NotFoundSourceException(id, "Address"));
    }

    public Optional<Person> getPersonByEmail(String email) {
        return personDao.findByEmail(email);
    }

    public Person getPerson(Integer id) {
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
                .orElseThrow(() -> new NotFoundSourceException(dishId, "Favorite"));

        person.getFavorites().remove(favorite);
        personDao.save(person);
    }

    @Transactional
    public void deletePerson(int id) {
        refreshTokenService.deleteByPerson(id);
        var person = getPerson(id);
        personDao.delete(person);
    }

    public List<Dish> getFavoriteDishesForPerson(String email) {
        var person = personDao.findByEmail(email)
                .orElseThrow(() -> new NotFoundSourceException("Person"));


        var favoritesDishesIds = person.getFavorites().stream()
                .map(Favorite::getDishId)
                .collect(toSet());

        return dishService.getDishes(favoritesDishesIds);
    }
}