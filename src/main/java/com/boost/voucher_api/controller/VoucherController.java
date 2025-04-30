package com.boost.voucher_api.controller;

import com.boost.voucher_api.dto.CreateVoucherRequest;
import com.boost.voucher_api.model.*;
import com.boost.voucher_api.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Handles creating and redeeming of vouchers
 * Acts as a REST controller for the /vouchers API.
 */
@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    /**
     * Endpoint to create a new voucher.
     *
     * Accepts a request body containing the email, the offer name, and the expiration date.
     * Voucher creation logic is done in the service layer VoucherService
     *
     * @param request DTO containing recipientEmail, offerName, and expirationDate
     * @return HTTP 201 Created response with the created voucher
     */
    @PostMapping("/create")
    public ResponseEntity<Voucher> create(@RequestBody CreateVoucherRequest request) {
        Voucher voucher = voucherService.createVoucherByEmailAndOfferName(request.getRecipientEmail(), request.getOfferName(), request.getExpirationDate());
        return ResponseEntity.status(201).body(voucher);
    }

    /**
     * Endpoint to redeem a voucher by a code.
     *
     * Voucher redemption is done in the service layer VoucherService
     *
     * @param code voucher code to redeem
     * @return HTTP 201 Created response with the updated voucher object
     */
    @PostMapping("/redeem/{code}")
    public ResponseEntity<Voucher> redeem(@PathVariable String code) {
        Voucher voucher = voucherService.useVoucher(code);
        return ResponseEntity.status(201).body(voucher);
    }
}