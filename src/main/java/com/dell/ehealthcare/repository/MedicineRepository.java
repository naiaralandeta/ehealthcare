package com.dell.ehealthcare.repository;

import com.dell.ehealthcare.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> getMedicinesByExpireLessThan(ZonedDateTime date);

    Medicine findMedicineByIdAndExpireLessThan(Long id, ZonedDateTime date);

    List<Medicine> findMedicinesByUsesContains(String uses);

    List<Medicine> findMedicinesByDiseaseContains(String disease);

    @Query(value = "select * from Medicine as m where year(m.inserted) = :year and month(m.inserted) = :month", nativeQuery = true)
    List<Medicine> findAllByInsertedMonth(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value = "select * from Medicine as m where year(m.inserted) = :date", nativeQuery = true)
    List<Medicine> findAllByInsertedYear(@Param("date") Integer date);

    List<Medicine> findAllByInsertedBetween(ZonedDateTime start, ZonedDateTime end);

    Boolean existsByName(String medicine);

}
