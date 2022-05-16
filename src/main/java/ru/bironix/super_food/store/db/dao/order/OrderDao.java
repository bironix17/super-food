
package ru.bironix.super_food.store.db.dao.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.store.db.models.order.Order;
import ru.bironix.super_food.store.db.models.order.OrderStatus;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
    List<Order> findByClient_IdOrderByCreatedDesc(Integer id, Pageable pageable);

    List<Order> findByStatusNot(OrderStatus orderStatus, Pageable pageable);

    List<Order> findByStatus(OrderStatus orderStatus, Pageable pageable);

    List<Order> findAllByClientId(int id, Pageable pageable);

}