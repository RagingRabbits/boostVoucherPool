package com.boost.voucher_api.dto;

import com.boost.voucher_api.model.Recipient;
import com.boost.voucher_api.model.SpecialOffer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateVoucherRequest {
    private Recipient recipient;
    private SpecialOffer offer;
    private LocalDate expirationDate;

    // Getters and setters
}