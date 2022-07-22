package com.dell.ehealthcare.controller;

import com.dell.ehealthcare.exceptions.BankAccountNotfoundException;
import com.dell.ehealthcare.exceptions.MedicineNotfoundException;
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
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private BankService bankService;

    @Autowired
    private CartService cartService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/data")
    public Optional<User> retrieveUserData(@RequestParam("id") Long id) throws UserNotfoundException {
        Optional<User> user = userService.findOne(id);

        if(user == null){
            throw new UserNotfoundException(String.format("User with ID %s not found", id));
        }
        return user;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = userService.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam("id") Long id){
        userService.deleteById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUserData(@RequestParam("id") Long id, @RequestBody User user){
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

    @GetMapping("/uses")
    public List<Medicine> findMedicineByUses(@RequestParam("uses") String uses) throws MedicineNotfoundException {
        List<Medicine> medicine = medicineService.findAllByUses(uses);
        if(medicine == null){
            throw new MedicineNotfoundException(String.format("Medicine with uses %s not found", uses));
        }
        return medicine;
    }

    @GetMapping("/disease")
    public List<Medicine> findMedicineByDisease(@RequestParam("disease") String disease) throws MedicineNotfoundException {
        List<Medicine> medicine = medicineService.findAllByDisease(disease);
        if(medicine == null){
            throw new MedicineNotfoundException(String.format("Medicine with disease %s not found", disease));
        }
        return medicine;
    }

    @GetMapping("/bank-account")
    public BankAccount retrieveBankAccountData(@RequestParam("id") Long userId) throws BankAccountNotfoundException {
        BankAccount bankAccount = bankService.findByUserAccount(userId);
        if(bankAccount == null){
            throw new BankAccountNotfoundException(String.format("Bank account with id %s not found", userId));
        }
        return bankAccount;
    }

    @PostMapping("/bank-account")
    public ResponseEntity<Object> createBankAccount(@Valid @RequestBody BankAccount bankAccount){
        BankAccount savedBankAccount = bankService.save(bankAccount);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedBankAccount.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/bank-account")
    public ResponseEntity<Object> updateBankAccountAmount(@RequestParam("id") Long id, @RequestParam("account") String account,  @Param("funds") Double funds){
        BankAccount bankAccountData = bankService.findByUserAndAccount(id, account);

        if(bankAccountData != null){
            bankAccountData.setFunds(bankAccountData.getFunds() + funds);
            return new ResponseEntity<>(bankService.save(bankAccountData), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/order")
    public ResponseEntity<Object> updateOrderData(@RequestParam("id") Long id, @RequestBody Cart cart){
        Optional<Cart> cartData = cartService.findOne(id);

        if(cartData.isPresent()){
            Cart updatedCart = cartData.get();

            return new ResponseEntity<>(cartService.save(updatedCart), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/order")
    public void deleteCart(@RequestParam("id") Long id){
        cartService.deleteById(id);
    }

    @GetMapping("/funds")
    public @ResponseBody Double retrieveFunds(@RequestParam("id") Long id) throws BankAccountNotfoundException {
        BankAccount bank = bankService.findByUserAccount(id);

        if(bank == null){
            throw new BankAccountNotfoundException(String.format("Bank account not found"));
        }
        return bank.getFunds();
    }

    @PostMapping("/funds")
    public ResponseEntity<Object> addFunds(@RequestParam("id") Long id, @RequestParam("funds") Double funds){
        BankAccount bank = bankService.findByUserAccount(id);

        if(bank == null){
            throw new BankAccountNotfoundException(String.format("Bank account not found"));
        }

        bank.setFunds(bank.getFunds() + funds);

        return new ResponseEntity<>(bankService.save(bank), HttpStatus.OK);

    }

}
