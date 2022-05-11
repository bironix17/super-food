
package ru.bironix.super_food.db.dao.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.models.order.Order;
import ru.bironix.super_food.db.models.order.OrderStatus;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
    List<Order> findByClient_IdOrderByCreatedDesc(Integer id);
    List<Order> findByOrderStatusNot(OrderStatus orderStatus);

    List<Order> findByOrderStatus(OrderStatus orderStatus);
    List<Order> findAllByClientId(int id);

}
