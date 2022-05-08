package ru.bironix.super_food.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.db.dao.person.PersonDao;
import ru.bironix.super_food.db.models.person.Person;

@Service("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonDao personDao;

    @Autowired
    public CustomUserDetailsService(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personDao.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(ApiError.INCORRECT_EMAIL_OR_PASSWORD.name()));
        return toSecurityUser(person);
    }

    private UserDetails toSecurityUser(Person p) {
        return new MySecurityUser(p.getEmail(),
                p.getPassword(),
                true,
                true,
                true,
                true,
                Role.User.getAuthorities(),
                p.getId()); // TODO fix me
    }
}
