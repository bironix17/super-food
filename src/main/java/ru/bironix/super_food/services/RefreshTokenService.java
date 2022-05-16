package ru.bironix.super_food.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.exceptions.ApiException;
import ru.bironix.super_food.store.db.dao.person.PersonDao;
import ru.bironix.super_food.store.db.dao.person.RefreshTokenDao;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.store.db.models.person.RefreshToken;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
        var refreshToken = refreshTokenDao.findByPerson(person).orElse(null);

        if (refreshToken != null) {
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(validityInSeconds));
            return refreshToken;
        }

        refreshToken = RefreshToken.builder()
                .person(person)
                .expiryDate(LocalDateTime.now().plusSeconds(validityInSeconds))
                .token(UUID.randomUUID().toString())
                .build();

        refreshToken = refreshTokenDao.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenDao.delete(token);
            throw new ApiException(ApiError.REFRESH_TOKEN_IS_EXPIRED);
        }
        return token;
    }

    public void deleteByPerson(Integer personId) {
        refreshTokenDao.deleteByPerson_Id(personId);
    }
}