package com.dell.ehealthcare.repository;

import com.dell.ehealthcare.model.MedicineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineHistoryRepository extends JpaRepository<MedicineHistory, Long> {

    MedicineHistory findMedicineHistoryByMedicineId(Long id);

    void deleteByMedicineId(Long id);
}
