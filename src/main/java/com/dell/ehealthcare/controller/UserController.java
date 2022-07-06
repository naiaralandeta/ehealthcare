package com.dell.ehealthcare.controller;

import com.dell.ehealthcare.exceptions.BankAccountNotfoundException;
import com.dell.ehealthcare.exceptions.MedicineNotfoundException;
import com.dell.ehealthcare.exceptions.OrderNotfoundException;
import com.dell.ehealthcare.exceptions.UserNotfoundException;
import com.dell.ehealthcare.model.*;
import com.dell.ehealthcare.services.BankService;
import com.dell.ehealthcare.services.CartService;
import com.dell.ehealthcare.services.MedicineService;
import com.dell.ehealthcare.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private BankService bankService;

    @Autowired
    private CartService cartService;

    @GetMapping("/api/user")
    public List<User> retrieveAllUsers(){
        return userService.findAll();
    }

    @GetMapping("api/user/{id}")
    public Optional<User> retrieveUserData(@PathVariable Long id) throws UserNotfoundException {
        Optional<User> user = userService.findOne(id);

        if(user == null){
            throw new UserNotfoundException(String.format("User with ID %s not found", id));
        }
        return user;
    }

    @PostMapping("api/user")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = userService.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("api/user/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<Object> updateUserData(@PathVariable("id") Long id, @RequestBody User user){
        Optional<User> userData = userService.findOne(id);

        if(userData.isPresent()){
            User updatedUser = userData.get();
            updatedUser.setPassword(user.getPassword());
            updatedUser.setAddress(user.getAddress());
            updatedUser.setPhone(user.getPhone());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setDob(user.getDob());
            updatedUser.setFirstname(user.getFirstname());
            updatedUser.setLastname(user.getLastname());

            return new ResponseEntity<>(userService.save(updatedUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/user/uses/{uses}")
    public List<Medicine> findMedicineByUses(@PathVariable("uses") String uses) throws MedicineNotfoundException{
        List<Medicine> medicine = medicineService.findAllByUses(uses);
        if(medicine == null){
            throw new MedicineNotfoundException(String.format("Medicine with uses %s not found", uses));
        }
        return medicine;
    }

    @GetMapping("/api/user/disease/{disease}")
    public List<Medicine> findMedicineByDisease(@PathVariable("disease") String disease) throws MedicineNotfoundException{
        List<Medicine> medicine = medicineService.findAllByDisease(disease);
        if(medicine == null){
            throw new MedicineNotfoundException(String.format("Medicine with disease %s not found", disease));
        }
        return medicine;
    }

    @GetMapping("/api/user/bank-account/{id}")
    public Optional<BankAccount> retreiveBankAccountData(@PathVariable("id") Long id) throws BankAccountNotfoundException {
        Optional<BankAccount> bankAccount = bankService.findOne(id);
        if(bankAccount == null){
            throw new BankAccountNotfoundException(String.format("Bank account with id %s not found", id));
        }
        return bankAccount;
    }

    @PostMapping("/api/user/bank-account")
    public ResponseEntity<Object> createBankAccount(@Valid @RequestBody BankAccount bankAccount){
        BankAccount savedBankAccount = bankService.save(bankAccount);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedBankAccount.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/api/user/bank-account")
    public ResponseEntity<Object> updateBankAccountAmount(@Param("account") String account, @Param("funds") Double funds){
        BankAccount bankAccountData = bankService.findByAccountNumber(account);

        if(bankAccountData != null){
            bankAccountData.setFunds(bankAccountData.getFunds() + funds);
            return new ResponseEntity<>(bankService.save(bankAccountData), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/user/order")
    public ResponseEntity<Object> createOrder(@RequestBody Cart cart){

        Cart savedCart = cartService.save(cart);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedCart.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

    @GetMapping("/api/user/order")
    public ResponseEntity<List<Item>> retrieveAllOrders(@Param("id") Long id){

        List<Cart> orders = cartService.findAll(id);

        if(!orders.isEmpty()){
            return new ResponseEntity(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/api/user/order/{id}")
    public ResponseEntity<Object> updateOrderData(@PathVariable("id") Long id, @RequestBody Cart cart){
        Optional<Cart> cartData = cartService.findOne(id);

        if(cartData.isPresent()){
            Cart updatedCart = cartData.get();
            //updatedCart.setQuantity(item.getQuantity());

            return new ResponseEntity<>(cartService.save(updatedCart), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("api/user/order/{id}")
    public void deleteCart(@PathVariable Long id){
        cartService.deleteById(id);
    }

}
