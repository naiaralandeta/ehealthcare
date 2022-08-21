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

        if(user.isPresent()) {
            Cart newCart = new Cart(cart.getOwner(), cart.getMedname(), cart.getQuantity(), OrderStatus.ORDERED, cart.getTotal(), ZonedDateTime.now(), cart.getPrice(), cart.getDiscount());
            newCart.setTotal(newCart.getTotal() + ((newCart.getPrice() * newCart.getQuantity()) - (newCart.getPrice() * newCart.getQuantity() * newCart.getDiscount()) / 100));
            Cart savedCart = cartService.save(newCart);
            return new ResponseEntity<>(savedCart, HttpStatus.OK);
        } else {
            throw new UserNotfoundException(String.format("User with ID %s not found", cart.getOwner()));
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
    public ResponseEntity<Object> updateQuantity(@RequestParam("cartId") Long cartId, @RequestParam("medicineId") Long medicineId, @RequestParam("quantity") Integer quantity){
        Optional<Cart> cart = cartService.findOne(cartId);
        if(cart.isPresent()){
            cart.get().setTotal(0.0);
           /* for (Medicine medicine: cart.get().getMedicine()) {
                if(medicine.getId() == medicineId){
                    medicine.setQuantity(quantity);
                }
                cart.get().setTotal(cart.get().getTotal() + ((medicine.getPrice() * medicine.getQuantity()) - (medicine.getPrice() * medicine.getQuantity() * medicine.getDiscount()) / 100));
            }*/
            return new ResponseEntity<>(cartService.save(cart.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cart")
    public ResponseEntity<Object>  deleteMedicine(@RequestParam("cartId") Long id, @RequestParam("medicineId") Long medicineId){
        Optional<Cart> cart = cartService.findOne(id);
        if(cart.isPresent()){
            cart.get().setTotal(0.0);
            /*for (Medicine medicine: cart.get().getMedicine()) {
                if(medicine.getId() == medicineId){
                    cart.get().getMedicine().remove(medicine);
                } else {
                    cart.get().setTotal(cart.get().getTotal() + ((medicine.getPrice() * medicine.getQuantity()) - (medicine.getPrice() * medicine.getQuantity() * medicine.getDiscount()) / 100));
                }
            }*/
            return new ResponseEntity<>(cartService.save(cart.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutCart(@RequestParam("userId") Long userId, @RequestParam("total") Double total){
        BankAccount account = bankService.findByUserAccount(userId);
        if(account != null){
            if(account.getFunds() >= total){
                account.setFunds(account.getFunds() - total);
                bankService.save(account);
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
        List<Cart> carts = cartService.getAllOrders(userId);
        if(!carts.isEmpty()){
            return new ResponseEntity<>(carts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
