package com.dell.ehealthcare.model;

import com.dell.ehealthcare.model.enums.ReportRange;
import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stock;

    private String sales;

    private ReportRange range;
}