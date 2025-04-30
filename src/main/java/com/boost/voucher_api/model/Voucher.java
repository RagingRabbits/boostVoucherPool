package com.boost.voucher_api.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @ManyToOne
    private Recipient recipient;

    @ManyToOne
    private SpecialOffer specialOffer;

    private LocalDate expirationDate;

    private LocalDateTime usedAt;

}
