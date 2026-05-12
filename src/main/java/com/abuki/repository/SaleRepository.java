package com.abuki.repository;

import com.abuki.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByProductId(Long productId);

    List<Sale> findBySaleDateBetween(LocalDate from, LocalDate to);

    @Query("SELECT s FROM Sale s ORDER BY s.saleDate DESC, s.saleTime DESC")
    List<Sale> findAllOrderedByDate();

    @Modifying
    @Query("DELETE FROM Sale s WHERE s.product.id = :productId")
    int deleteByProductId(Long productId);

    @Query("SELECT COALESCE(SUM(s.total), 0) FROM Sale s WHERE s.saleDate BETWEEN :from AND :to")
    Double sumTotalBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT COALESCE(SUM(s.quantity), 0) FROM Sale s WHERE s.saleDate BETWEEN :from AND :to")
    Long sumQuantityBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT COUNT(s) FROM Sale s WHERE s.saleDate BETWEEN :from AND :to")
    Long countSalesBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT COALESCE(SUM(p.cost * s.quantity), 0) FROM Sale s JOIN s.product p WHERE s.saleDate BETWEEN :from AND :to")
    Double sumCogsBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT s.saleDate, COALESCE(SUM(s.total), 0), COALESCE(SUM(s.quantity), 0) FROM Sale s WHERE s.saleDate BETWEEN :from AND :to GROUP BY s.saleDate ORDER BY s.saleDate")
    List<Object[]> sumBySaleDate(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT YEAR(s.saleDate), MONTH(s.saleDate), COALESCE(SUM(s.total), 0), COALESCE(SUM(s.quantity), 0) FROM Sale s WHERE s.saleDate BETWEEN :from AND :to GROUP BY YEAR(s.saleDate), MONTH(s.saleDate) ORDER BY YEAR(s.saleDate), MONTH(s.saleDate)")
    List<Object[]> sumByYearMonth(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT s.saleDate, COALESCE(SUM(p.cost * s.quantity), 0) FROM Sale s JOIN s.product p WHERE s.saleDate BETWEEN :from AND :to GROUP BY s.saleDate ORDER BY s.saleDate")
    List<Object[]> sumCogsBySaleDate(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT YEAR(s.saleDate), MONTH(s.saleDate), COALESCE(SUM(p.cost * s.quantity), 0) FROM Sale s JOIN s.product p WHERE s.saleDate BETWEEN :from AND :to GROUP BY YEAR(s.saleDate), MONTH(s.saleDate) ORDER BY YEAR(s.saleDate), MONTH(s.saleDate)")
    List<Object[]> sumCogsByYearMonth(@Param("from") LocalDate from, @Param("to") LocalDate to);
}