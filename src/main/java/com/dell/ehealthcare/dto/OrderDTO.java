package com.dell.ehealthcare.dto;

import com.dell.ehealthcare.model.Medicine;
import com.dell.ehealthcare.model.enums.OrderStatus;

import java.io.Serializable;
import java.util.Set;

public class OrderDTO implements Serializable {

    private Set<Medicine> items;
    private OrderStatus status;

    public OrderDTO(Set<Medicine> medicine, OrderStatus status) {
        this.items = medicine;
        this.status = status;
    }

    public Set<Medicine> getItems() {
        return items;
    }

    public void setItems(Set<Medicine> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
