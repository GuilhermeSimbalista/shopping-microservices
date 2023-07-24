package br.com.compassuol.sp.challenge.msproduct.repositories;

import br.com.compassuol.sp.challenge.msproduct.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
