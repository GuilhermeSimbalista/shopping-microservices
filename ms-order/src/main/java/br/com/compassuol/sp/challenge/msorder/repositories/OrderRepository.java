package br.com.compassuol.sp.challenge.msorder.repositories;

import br.com.compassuol.sp.challenge.msorder.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
