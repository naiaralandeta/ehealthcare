package com.dell.ehealthcare.model;

import com.dell.ehealthcare.model.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CART")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinTable(name = "USER_CART",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

    @OneToMany
    private Set<Medicine> medicine = new HashSet<>();


    private OrderStatus status;

    private Double total;

    private ZonedDateTime date;

    public Cart(User user, Set<Medicine> medicine, OrderStatus status, double total, ZonedDateTime date) {
        this.user = user;
        this.medicine = medicine;
        this.status = status;
        this.total = total;
        this.date = date;
    }

}

