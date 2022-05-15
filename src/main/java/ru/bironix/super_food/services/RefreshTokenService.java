package ru.bironix.super_food.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.exceptions.ApiException;
import ru.bironix.super_food.exceptions.NotFoundSourceException;
import ru.bironix.super_food.store.UpdateMapper;
import ru.bironix.super_food.store.db.dao.person.AddressDao;
import ru.bironix.super_food.store.db.dao.person.FavoritesDao;
import ru.bironix.super_food.store.db.dao.person.PersonDao;
import ru.bironix.super_food.store.db.dao.person.RefreshTokenDao;
import ru.bironix.super_food.store.db.models.dish.Dish;
import ru.bironix.super_food.store.db.models.person.Address;
import ru.bironix.super_food.store.db.models.person.Favorite;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.store.db.models.person.RefreshToken;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Service
public class RefreshTokenService {

    private final PersonDao personDao;
    private final RefreshTokenDao refreshTokenDao;

    @Value("${jwt.refresh_token.expiration}")
    private long validityInSeconds;

    @Autowired
    public RefreshTokenService(PersonDao personDao,
                               RefreshTokenDao refreshTokenDao) {
        this.personDao = personDao;
        this.refreshTokenDao = refreshTokenDao;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenDao.findByToken(token);
    }

    public RefreshToken create(Person person) {
        refreshTokenDao.findByPerson(person)
                .ifPresent(refreshTokenDao::delete);

        var refreshToken = RefreshToken.builder()
                .person(person)
                .expiryDate(LocalDateTime.now().plusSeconds(validityInSeconds))
                .token(UUID.randomUUID().toString())
                .build();

        refreshToken = refreshTokenDao.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken deleteAndCreate(RefreshToken deletingRefreshToken) {
        var newRefreshToken = create(deletingRefreshToken.getPerson());
        refreshTokenDao.delete(deletingRefreshToken);
        return newRefreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenDao.delete(token);
            throw new ApiException(ApiError.REFRESH_TOKEN_IS_EXPIRED);
        }
        return token;
    }

    public void deleteByPerson(Person person) {
        refreshTokenDao.deleteByPerson(person);
    }
}