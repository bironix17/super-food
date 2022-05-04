package ru.bironix.super_food.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.db.dao.person.PersonDao;
import ru.bironix.super_food.db.models.person.Person;

@Service("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PersonDao personDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personDao.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        return toSecurityUser(person);
    }

    private UserDetails toSecurityUser(Person p) {
        return new User(p.getEmail(),
                p.getPassword(),
                true,
                true,
                true,
                true,
                Role.User.getAuthorities()); // TODO fix me
    }
}
