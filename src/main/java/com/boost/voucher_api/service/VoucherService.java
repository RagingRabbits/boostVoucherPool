package com.boost.voucher_api.service;

import com.boost.voucher_api.model.*;
import com.boost.voucher_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public Voucher createVoucher(Recipient recipient, SpecialOffer offer, LocalDate expirationDate) {
        Voucher voucher = new Voucher();
        voucher.setCode(UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());
        voucher.setRecipient(recipient);
        voucher.setSpecialOffer(offer);
        voucher.setExpirationDate(expirationDate);
        return voucherRepository.save(voucher);
    }

    public Voucher useVoucher(String code) {
        Voucher voucher = voucherRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));

        if (voucher.getExpirationDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Voucher expired");
        }

        if (voucher.getUsedAt() != null) {
            throw new RuntimeException("Voucher already used");
        }

        voucher.setUsedAt(LocalDateTime.now());
        return voucherRepository.save(voucher);
    }
}