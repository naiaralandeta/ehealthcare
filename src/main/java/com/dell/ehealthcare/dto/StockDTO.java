package com.dell.ehealthcare.dto;

import java.io.Serializable;

public class StockDTO implements Serializable {
	
    private Long reference;
    private String name;
    private Integer quantity;

    public StockDTO(Long reference, String name, Integer quantity) {
        this.reference = reference;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
