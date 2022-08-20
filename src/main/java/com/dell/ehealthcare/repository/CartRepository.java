package com.dell.ehealthcare.repository;

import com.dell.ehealthcare.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> getCartsByOwner(Long id);

    Cart getAllById(Long id);

    @Query(value = "select * from Cart as c JOIN user_cart u ON u.cart_id = c.id where year(c.date) = :year and month(c.date) = :month ", nativeQuery = true)
    List<Cart> findAllByDateMonth(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value = "select * from Cart as c JOIN user_cart u ON u.cart_id = c.id where year(c.date) = :year", nativeQuery = true)
    List<Cart> findAllByDateYear(@Param("year") Integer year);

    List<Cart> findAllByDateBetween(ZonedDateTime start, ZonedDateTime end);

}