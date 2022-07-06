package com.dell.ehealthcare.services;

import com.dell.ehealthcare.model.BankAccount;
import com.dell.ehealthcare.model.Item;
import com.dell.ehealthcare.repository.BankRepository;
import com.dell.ehealthcare.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public BankAccount save(BankAccount bankAccount) {
        return bankRepository.save(bankAccount);
    }

    public Optional<BankAccount> findOne(Long id){ return bankRepository.findById(id);}

    public BankAccount findByAccountNumber(String accountNumber){ return bankRepository.findByAccountNumber(accountNumber);}

    public void deleteById(Long id){
        bankRepository.deleteById(id);
    }
}
