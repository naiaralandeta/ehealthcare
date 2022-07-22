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
@Table(name = "MEDICINE")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(nullable = false)
    private ZonedDateTime inserted;
}
