package com.dell.ehealthcare.controller;

import com.dell.ehealthcare.exceptions.UserNotfoundException;
import com.dell.ehealthcare.model.Medicine;
import com.dell.ehealthcare.services.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping("/api/admin")
    public ResponseEntity<List<Medicine>> retrieveAllMedicines(){

        List<Medicine> medicines = medicineService.findAll();

        if(!medicines.isEmpty()){
            return new ResponseEntity<>(medicines, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/api/admin/{id}")
    public Optional<Medicine> retrieveMedicine(@PathVariable Long id){
        Optional<Medicine> medicine = medicineService.findOne(id);
        if(medicine == null){
            throw new UserNotfoundException(String.format("Medicine with ID %s not found", id));
        }
        return medicine;
    }

    @PostMapping("/api/admin")
    public ResponseEntity<Object> createMedicine(@RequestBody Medicine medicine){
        Medicine savedMedicine = medicineService.save(medicine);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedMedicine.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

    @DeleteMapping("api/admin/{id}")
    public void deleteMedicine(@PathVariable Long id){
        medicineService.deleteById(id);
    }

    @DeleteMapping("api/admin")
    public void deleteExpiredMedicine(){
        medicineService.deleteExpiredMedicine(ZonedDateTime.now());
    }

    @DeleteMapping("api/admin/demand")
    public void deleteMedicinesNoDemand(){
        medicineService.deleteMedicinesZeroDemand();
    }

    @PutMapping("/api/admin/update/{id}")
    public ResponseEntity<Object> updateMedicineData(@PathVariable("id") Long id, @RequestBody Medicine medicine){
        Optional<Medicine> medicineData = medicineService.findOne(id);

        if(medicineData.isPresent()){
            Medicine updatedMedicine = medicineData.get();
            updatedMedicine.setPrice(medicine.getPrice());
            updatedMedicine.setQuantity(medicine.getQuantity());
            updatedMedicine.setCompany(medicine.getCompany());

            return new ResponseEntity<>(medicineService.save(updatedMedicine), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/admin/{id}")
    public ResponseEntity<Object> updateDiscount(@PathVariable("id") Long id, @RequestBody Medicine medicine){
        Optional<Medicine> medicineData = medicineService.findOne(id);

        if(medicineData.isPresent()){
            Medicine updatedMedicine = medicineData.get();
            updatedMedicine.setDiscount(medicine.getDiscount());

            return new ResponseEntity<>(medicineService.save(updatedMedicine), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
