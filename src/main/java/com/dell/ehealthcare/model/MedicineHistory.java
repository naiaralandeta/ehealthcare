package com.dell.ehealthcare.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEDICINE_HISTORY")
public class MedicineHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long medicineId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private Double price;

    private Integer quantity;

    @Column(nullable = false)
    private String uses;

    @Column(nullable = false)
    private String disease;

    @Column(nullable = false)
    private ZonedDateTime expire;

    private Integer discount;

    public MedicineHistory(Long id, String name, String company, Double price, Integer quantity, String uses, String disease, ZonedDateTime expire, Integer discount) {
        this.medicineId = id;
        this.name = name;
        this.company = company;
        this.price = price;
        this.quantity = quantity;
        this.uses = uses;
        this.disease = disease;
        this.expire = expire;
        this.discount = discount;
    }
}
