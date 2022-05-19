package ru.bironix.super_food.store.db.dao.person;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bironix.super_food.store.db.models.person.Address;

import java.util.Optional;

public interface AddressDao extends JpaRepository<Address, Integer> {
    Optional<Address> findByAddress(String address);

}
