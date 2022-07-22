package com.dell.ehealthcare.services;

import com.dell.ehealthcare.model.MedicineHistory;
import com.dell.ehealthcare.repository.MedicineHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MedicineHistoryService {

    @Autowired
    private MedicineHistoryRepository medicineHistoryRepository;

    public void deleteMedicine(Long id){
        medicineHistoryRepository.deleteById(id);
    }

    public void deleteByMedicineId(Long id){
        medicineHistoryRepository.deleteByMedicineId(id);
    }

    public void saveMedicineHistory(MedicineHistory medicine){
        medicineHistoryRepository.save(medicine);
    }

    public MedicineHistory getMedicine(Long id){
        return medicineHistoryRepository.findMedicineHistoryByMedicineId(id);
    }
}
