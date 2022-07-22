package com.dell.ehealthcare.services;

import com.dell.ehealthcare.model.BankAccount;
import com.dell.ehealthcare.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public BankAccount save(BankAccount bankAccount) {
        return bankRepository.save(bankAccount);
    }

    public Optional<BankAccount> findByUserID(Long id){ return bankRepository.findById(id);}

    public BankAccount findByUserAccount(Long id){ return bankRepository.findByUserId(id);}

    public BankAccount findByUserAndAccount(Long id, String account){ return bankRepository.findBankAccountByUser_IdAndAccountNumber(id, account);}

    public void deleteById(Long id){
        bankRepository.deleteById(id);
    }
}

