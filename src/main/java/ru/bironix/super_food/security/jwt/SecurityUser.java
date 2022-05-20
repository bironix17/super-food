package ru.bironix.super_food.security.jwt;

import org.springframework.security.core.userdetails.User;
import ru.bironix.super_food.store.db.models.person.Role;

public class SecurityUser extends User {

    private final int id;
    private final Role role;

    public SecurityUser(String username,
                        String password,
                        boolean enabled,
                        boolean accountNonExpired,
                        boolean credentialsNonExpired,
                        boolean accountNonLocked,
                        Role role,
                        int id) {
        super(username,
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                role.getAuthority());
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
}
