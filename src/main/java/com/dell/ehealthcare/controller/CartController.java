package com.dell.ehealthcare.controller;


import com.dell.ehealthcare.dto.OrderDTO;
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
    public ResponseEntity<Object> addMedicine(@RequestParam("id") Long id, @RequestBody Set<Medicine> medicine){
        Optional<User> user = userService.findOne(id);
        if(user.isPresent()) {
            Cart cart = new Cart(user.get(), medicine, OrderStatus.ORDERED,0.0, ZonedDateTime.now());
            for (Medicine med: medicine) {
                cart.setTotal(cart.getTotal() + ((med.getPrice() * med.getQuantity()) - (med.getPrice() * med.getQuantity() * med.getDiscount()) / 100));
            }
            cartService.save(cart);
            return new ResponseEntity<>(cartService.save(cart), HttpStatus.OK);
        } else {
            throw new UserNotfoundException(String.format("User with ID %s not found", id));
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<Set<Medicine>> retrieveMedicines(@RequestParam Long id){
        Cart cart = cartService.getAllMedicines(id);
        if(cart != null){
            return new ResponseEntity<>(cart.getMedicine(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/cart")
    public ResponseEntity<Object> updateQuantity(@RequestParam("cartId") Long cartId, @RequestParam("medicineId") Long medicineId, @RequestParam("quantity") Integer quantity){
        Optional<Cart> cart = cartService.findOne(cartId);
        if(cart.isPresent()){
            cart.get().setTotal(0.0);
            for (Medicine medicine: cart.get().getMedicine()) {
                if(medicine.getId() == medicineId){
                    medicine.setQuantity(quantity);
                }
                cart.get().setTotal(cart.get().getTotal() + ((medicine.getPrice() * medicine.getQuantity()) - (medicine.getPrice() * medicine.getQuantity() * medicine.getDiscount()) / 100));
            }
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
            for (Medicine medicine: cart.get().getMedicine()) {
                if(medicine.getId() == medicineId){
                    cart.get().getMedicine().remove(medicine);
                } else {
                    cart.get().setTotal(cart.get().getTotal() + ((medicine.getPrice() * medicine.getQuantity()) - (medicine.getPrice() * medicine.getQuantity() * medicine.getDiscount()) / 100));
                }
            }
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
            Set<OrderDTO> orders = new HashSet<>();
            carts.forEach(cart -> orders.add(new OrderDTO(cart.getMedicine(), cart.getStatus())));
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            throw new UserNotfoundException(String.format("User with ID %s not found", userId));
        }
    }
}
