package com.abuki.repository;

import com.abuki.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    List<Product> findByStatus(String status);

    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
           "LOWER(p.sku)  LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
           "LOWER(p.category) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    List<Product> search(String keyword);

    @Query("SELECT p FROM Product p WHERE p.stock > 0 AND p.stock <= p.minStock")
    List<Product> findLowStock();

    @Query("SELECT p FROM Product p WHERE p.stock = 0")
    List<Product> findOutOfStock();
}