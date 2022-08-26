package com.dell.ehealthcare.services;

import com.dell.ehealthcare.model.Medicine;
import com.dell.ehealthcare.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Medicine> deleteExpiredMedicines(ZonedDateTime dateTime){
        return medicineRepository.getMedicinesByExpireLessThan(dateTime);
    }

    public Medicine deleteExpiredMedicine(Long id, ZonedDateTime dateTime){
        return medicineRepository.findMedicineByIdAndExpireLessThan(id, dateTime);
    }

    public List<Medicine> findAllByUses(String uses){
        return medicineRepository.findMedicinesByUsesContains(uses) ;
    }

    public List<Medicine> findAllByDisease(String disease){
        return medicineRepository.findMedicinesByDiseaseContains(disease) ;
    }

    public List<Medicine> findByYear(ZonedDateTime date){
        return medicineRepository.findAllByInsertedYear(date.getYear());
    }

    public List<Medicine> findByMonth(ZonedDateTime date){
        return medicineRepository.findAllByInsertedMonth(date.getMonthValue(), date.getYear());
    }

    public List<Medicine> findByBetween(ZonedDateTime startDate, ZonedDateTime endDate){
        return medicineRepository.findAllByInsertedBetween(startDate, endDate);
    }

    public Boolean existsByName(String medicine){
        return medicineRepository.existsByName(medicine);
    }

}
