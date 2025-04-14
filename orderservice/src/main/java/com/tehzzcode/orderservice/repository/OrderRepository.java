package com.tehzzcode.orderservice.repository;

import com.tehzzcode.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
