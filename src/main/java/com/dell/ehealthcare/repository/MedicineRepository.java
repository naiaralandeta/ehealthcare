package com.dell.ehealthcare.repository;

import com.dell.ehealthcare.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    void deleteMedicinesByExpireLessThan(ZonedDateTime date);

    List<Medicine> findMedicinesByUsesEquals(String uses);

    List<Medicine> findMedicinesByDiseaseEquals(String disease);
}
