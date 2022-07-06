package com.dell.ehealthcare.services;

import com.dell.ehealthcare.model.Medicine;
import com.dell.ehealthcare.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public List<Medicine> findAll(){
        return medicineRepository.findAll();
    }

    public Medicine save(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public Optional<Medicine> findOne(Long id){
        return medicineRepository.findById(id);
    }

    public void deleteById(Long id){
        medicineRepository.deleteById(id);
    }

    public void deleteExpiredMedicine(ZonedDateTime dateTime){
       medicineRepository.deleteMedicinesByExpireLessThan(dateTime);
    }

    public void deleteMedicinesZeroDemand(){
        //medicineRepository.deleteMedicinesByExpireLessThan();
    }

    public List<Medicine> findAllByUses(String uses){
        return medicineRepository.findMedicinesByUsesEquals(uses) ;
    }


    public List<Medicine> findAllByDisease(String disease){
        return medicineRepository.findMedicinesByDiseaseEquals(disease) ;
    }
}
