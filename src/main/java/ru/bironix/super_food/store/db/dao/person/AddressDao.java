package ru.bironix.super_food.store.db.dao.person;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bironix.super_food.store.db.models.person.Address;

public interface AddressDao extends JpaRepository<Address, Integer> {
}
