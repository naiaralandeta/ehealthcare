package com.dell.ehealthcare.controller;

import com.dell.ehealthcare.exceptions.MedicineNotfoundException;
import com.dell.ehealthcare.model.Medicine;
import com.dell.ehealthcare.model.MedicineHistory;
import com.dell.ehealthcare.services.MedicineHistoryService;
import com.dell.ehealthcare.services.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MedicineHistoryService medicineHistoryService;

    @GetMapping("/medicines")
    public ResponseEntity<List<Medicine>> retrieveAllMedicines(){

        List<Medicine> medicines = medicineService.findAll();

        if(!medicines.isEmpty()){
            return new ResponseEntity<>(medicines, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/medicine")
    public Optional<Medicine> retrieveMedicine(@RequestParam("medicineId") Long id){
        Optional<Medicine> medicine = medicineService.findOne(id);
        if(medicine == null){
            throw new MedicineNotfoundException(String.format("Medicine with ID %s not found", id));
        }
        return medicine;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createMedicine(@RequestBody Medicine medicine){
        Medicine savedMedicine = medicineService.save(medicine);

        if(savedMedicine == null){
            throw new MedicineNotfoundException();
        }

        medicineHistoryService.saveMedicineHistory(new MedicineHistory(savedMedicine.getId(), savedMedicine.getName(), savedMedicine.getCompany(), savedMedicine.getPrice(),
                savedMedicine.getQuantity(), savedMedicine.getUses(), savedMedicine.getDisease(), savedMedicine.getExpire(), savedMedicine.getDiscount()));

        return ResponseEntity.ok(savedMedicine);
    }

    @DeleteMapping("/delete")
    public void deleteMedicine(@RequestParam("id") Long id){
        medicineService.deleteById(id);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllExpiredMedicines(){
        List<Medicine> medicines = medicineService.deleteExpiredMedicines(ZonedDateTime.now());

        if(!medicines.isEmpty()){
            for(Medicine medicine: medicines){
                medicineService.deleteById(medicine.getId());
                medicineHistoryService.deleteByMedicineId(medicine.getId());
            }
            return ResponseEntity.ok("Medicines removed");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/expired")
    public ResponseEntity<?> deleteAnExpiredMedicine(@RequestParam("id") Long id){
        Medicine medicine = medicineService.deleteExpiredMedicine(id, ZonedDateTime.now());

        if(medicine != null){
            medicineService.deleteById(medicine.getId());
            medicineHistoryService.deleteByMedicineId(medicine.getId());
            return ResponseEntity.ok("Medicine removed");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/demand")
    public ResponseEntity<?> deleteMedicinesNoDemand(@RequestParam("id") Long id){
        Optional<Medicine> medicineData = medicineService.findOne(id);
        MedicineHistory mHistory = medicineHistoryService.getMedicine(id);

        if(medicineData.isPresent() && mHistory != null && medicineData.get().getQuantity() == mHistory.getQuantity()){
            medicineHistoryService.deleteMedicine(mHistory.getId());
            medicineService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateMedicineData(@RequestParam("id") Long id, @RequestBody Medicine medicine){
        Optional<Medicine> medicineData = medicineService.findOne(id);
        MedicineHistory mHistory = medicineHistoryService.getMedicine(id);

        if(medicineData.isPresent()){
            Medicine updatedMedicine = medicineData.get();
            updatedMedicine.setPrice(medicine.getPrice());
            updatedMedicine.setQuantity(medicineData.get().getQuantity() + medicine.getQuantity());
            updatedMedicine.setCompany(medicine.getCompany());

            mHistory.setPrice(medicine.getPrice());
            mHistory.setQuantity(medicineData.get().getQuantity() + medicine.getQuantity());
            mHistory.setCompany(medicine.getCompany());

            medicineHistoryService.saveMedicineHistory(mHistory);

            return new ResponseEntity<>(medicineService.save(updatedMedicine), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/discount")
    public ResponseEntity<Object> updateDiscount(@RequestParam("id") Long id, @RequestBody Medicine medicine){
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
