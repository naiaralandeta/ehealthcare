package com.dell.ehealthcare.controller;


import com.dell.ehealthcare.exceptions.UserNotfoundException;
import com.dell.ehealthcare.model.BankAccount;
import com.dell.ehealthcare.model.Cart;
import com.dell.ehealthcare.model.Medicine;
import com.dell.ehealthcare.model.User;
import com.dell.ehealthcare.model.enums.OrderStatus;
import com.dell.ehealthcare.payload.response.MessageResponse;
import com.dell.ehealthcare.services.BankService;
import com.dell.ehealthcare.services.CartService;
import com.dell.ehealthcare.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankService bankService;

    @PostMapping("/cart")
    public ResponseEntity<Object> addMedicine(@RequestBody Cart cart){
        Optional<User> user = userService.findOne(cart.getOwner());
        Boolean existMedicine = cartService.existsByMednameAndStatus(cart.getMedname(), OrderStatus.PENDING);

        if(user.isPresent() & !existMedicine) {
            Cart newCart = new Cart(cart.getOwner(), cart.getMedname(), cart.getQuantity(), OrderStatus.PENDING, cart.getTotal(), ZonedDateTime.now(), cart.getPrice(), cart.getDiscount());

            Double total = newCart.getTotal() + ((newCart.getPrice() * newCart.getQuantity()) - (newCart.getPrice() * newCart.getQuantity() * newCart.getDiscount()) / 100);
            newCart.setTotal((double) Math.round(total * 100.0) / 100.0);

            Cart savedCart = cartService.save(newCart);
            return new ResponseEntity<>(savedCart, HttpStatus.OK);
        } else {
            throw new UserNotfoundException(String.format("User with ID %s couldn't add a medicine", cart.getOwner()));
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<List<Cart>> retrieveMedicines(@RequestParam Long id){
        List<Cart> carts = cartService.getAllMedicines(id);
        if(carts != null){
            return new ResponseEntity<>(carts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/cart")
    public ResponseEntity<Object> updateQuantity(@RequestParam("cartId") Long cartId, @RequestParam("quantity") Integer quantity){
        Optional<Cart> cart = cartService.findOne(cartId);
        if(cart.isPresent()){
            cart.get().setQuantity(quantity);
            cart.get().setTotal(0.0);
            Double total = cart.get().getTotal() + ((cart.get().getPrice() * quantity) - (cart.get().getPrice() * quantity * cart.get().getDiscount()) / 100);
            cart.get().setTotal((double) Math.round(total * 100.0) / 100.0);
            cartService.save(cart.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cart")
    public ResponseEntity<Object>  deleteMedicine(@RequestParam("cartId") Long id){
        Optional<Cart> cart = cartService.findOne(id);
        if(cart.isPresent()){
            cartService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutCart(@RequestParam("userId") Long userId, @RequestParam("total") Double total, @RequestBody Set<Cart> orders){
        BankAccount account = bankService.findByUserAccount(userId);
        if(account != null){
            if(account.getFunds() >= total){
                account.setFunds(account.getFunds() - total);
                bankService.save(account);
                for(Cart order: orders){
                    Optional<Cart> cart = cartService.findOne(order.getId());
                    if(cart.isPresent()){
                        cart.get().setStatus(OrderStatus.ORDERED);
                        cartService.save(cart.get());
                    }
                }
                return ResponseEntity.ok(new MessageResponse("Payment successfully!"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Insufficient amount!"));
            }
        } else {
            throw new UserNotfoundException(String.format("User with ID %s not found", userId));
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders(@RequestParam("userId") Long userId){
        List<Cart> carts = cartService.getAllOrders(userId, OrderStatus.PENDING);
        if(!carts.isEmpty()){
            return new ResponseEntity<>(carts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/pending-orders")
    public ResponseEntity<?> getAllPendingOrders(@RequestParam("userId") Long userId){
        List<Cart> carts = cartService.getAllPendingOrders(userId, OrderStatus.PENDING);
        if(!carts.isEmpty()){
            return new ResponseEntity<>(carts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
