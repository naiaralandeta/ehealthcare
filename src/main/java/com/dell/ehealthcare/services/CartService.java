package com.dell.ehealthcare.services;

import com.dell.ehealthcare.model.BankAccount;
import com.dell.ehealthcare.model.Cart;
import com.dell.ehealthcare.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<Cart> findOne(Long id){ return cartRepository.findById(id);}

    public List<Cart> findAll(Long id){ return cartRepository.findByUser(id);}

    public void deleteById(Long id){
        cartRepository.deleteById(id);
    }
}
