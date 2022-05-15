package ru.bironix.super_food.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUser extends User {

    private final int id;

    public SecurityUser(String username,
                        String password,
                        Collection<? extends GrantedAuthority> authorities,
                        int id) {
        super(username, password, authorities);
        this.id = id;
    }

    public SecurityUser(String username,
                        String password,
                        boolean enabled,
                        boolean accountNonExpired,
                        boolean credentialsNonExpired,
                        boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities,
                        int id) {
        super(username,
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
