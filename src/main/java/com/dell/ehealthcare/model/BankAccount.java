package com.dell.ehealthcare.model;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BANK")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    private Double funds;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BankAccount(String accountNum, Double funds, User user) {
        this.accountNumber = accountNum;
        this.funds = funds + 1000;
        this.user = user;
    }
}
