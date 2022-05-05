
package ru.bironix.super_food.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.models.order.Order;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
    List<Order> findAllByClientId(int id);
}
