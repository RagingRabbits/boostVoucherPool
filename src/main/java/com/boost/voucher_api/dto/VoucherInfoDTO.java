package com.boost.voucher_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherInfoDTO {
    private String code;
    private String offerName;
}