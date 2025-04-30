package com.boost.voucher_api.model;
import jakarta.persistence.*;

@Entity
public class SpecialOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double percentageDiscount;

}