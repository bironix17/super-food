package ru.bironix.super_food.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.exceptions.ApiException;
import ru.bironix.super_food.security.loginAttempt.LoginAttemptService;
import ru.bironix.super_food.store.db.dao.person.PersonDao;
import ru.bironix.super_food.store.db.models.person.Person;

import javax.servlet.http.HttpServletRequest;

import static ru.bironix.super_food.utils.Utils.getClientIP;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private HttpServletRequest request;
    private final PersonDao personDao;
    private final LoginAttemptService loginAttemptService;

    @Autowired
    public CustomUserDetailsService(PersonDao personDao, LoginAttemptService loginAttemptService) {
        this.personDao = personDao;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String ip = getClientIP(request);
        if (loginAttemptService.isBlocked(ip)) {
            throw new ApiException(ApiError.ALLOWED_NUMBER_OF_LOGIN_ATTEMPTS_EXCEEDED);
        }
        Person person = personDao.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(ApiError.INCORRECT_EMAIL_OR_PASSWORD.name()));
        return toSecurityUser(person);
    }


    private UserDetails toSecurityUser(Person p) {
        return new SecurityUser(p.getEmail(),
                p.getPassword(),
                true,
                true,
                true,
                !p.getBanned(),
                p.getRole().getAuthority(),
                p.getId());
    }
}
