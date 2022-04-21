package ru.bironix.super_food.db.dao.person;

import org.springframework.data.repository.CrudRepository;
import ru.bironix.super_food.db.models.person.Address;

public interface AddressDao extends CrudRepository<Address, Integer> {
}
