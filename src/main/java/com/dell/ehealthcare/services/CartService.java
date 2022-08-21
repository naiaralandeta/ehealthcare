package com.dell.ehealthcare.services;

import com.dell.ehealthcare.model.Cart;
import com.dell.ehealthcare.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> findOne(Long id){
        return cartRepository.findById(id);
    }

    public List<Cart> getAllOrders(Long id){
        return cartRepository.getCartsByOwner(id);
    }

    public List<Cart> getAllMedicines(Long id){
        return cartRepository.getAllById(id);
    }

    public void deleteById(Long id){
        cartRepository.deleteById(id);
    }

    public List<Cart> findByYear(ZonedDateTime date){
        return cartRepository.findAllByDateYear(date.getYear());
    }

    public List<Cart> findByMonth(ZonedDateTime date){
        return cartRepository.findAllByDateMonth(date.getMonthValue(), date.getYear());
    }

    public List<Cart> findByBetween(ZonedDateTime startDate, ZonedDateTime endDate){
        return cartRepository.findAllByDateBetween(startDate, endDate);
    }
}
