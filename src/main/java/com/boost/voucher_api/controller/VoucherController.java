package com.boost.voucher_api.controller;

import com.boost.voucher_api.dto.CreateVoucherRequest;
import com.boost.voucher_api.model.*;
import com.boost.voucher_api.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @PostMapping("/create")
    public ResponseEntity<Voucher> create(@RequestBody CreateVoucherRequest req) {
        Voucher voucher = voucherService.createVoucher(req.getRecipient(), req.getOffer(), req.getExpirationDate());
        return ResponseEntity.ok(voucher);
    }

    @PostMapping("/redeem/{code}")
    public ResponseEntity<Voucher> redeem(@PathVariable String code) {
        return ResponseEntity.ok(voucherService.useVoucher(code));
    }
}