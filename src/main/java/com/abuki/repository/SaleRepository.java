package com.abuki.repository;

import com.abuki.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByProductId(Long productId);

    @Query("SELECT s FROM Sale s ORDER BY s.saleDate DESC, s.saleTime DESC")
    List<Sale> findAllOrderedByDate();

    @Modifying
    @Query("DELETE FROM Sale s WHERE s.product.id = :productId")
    int deleteByProductId(Long productId);
}