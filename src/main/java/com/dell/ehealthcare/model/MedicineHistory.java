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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getUses() {
		return uses;
	}

	public void setUses(String uses) {
		this.uses = uses;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public ZonedDateTime getExpire() {
		return expire;
	}

	public void setExpire(ZonedDateTime expire) {
		this.expire = expire;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
    
}
