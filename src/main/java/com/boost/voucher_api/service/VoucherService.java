package com.boost.voucher_api.service;

import com.boost.voucher_api.model.*;
import com.boost.voucher_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private RecipientRepository recipientRepository;

    @Autowired
    private SpecialOfferRepository offerRepository;

//    public Voucher createVoucher(Long recipientId, Long offerId, LocalDate expirationDate) {
//        Recipient recipient = recipientRepository.findById(recipientId)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipient not found"));
//        SpecialOffer offer = offerRepository.findById(offerId)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Special offer not found"));
//        Voucher voucher = new Voucher();
//        voucher.setCode(UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());
//        voucher.setRecipient(recipient);
//        voucher.setSpecialOffer(offer);
//        voucher.setExpirationDate(expirationDate);
//        return voucherRepository.save(voucher);
//    }

    public Voucher createVoucherByEmailAndOfferName(String recipientEmail, String offerName, LocalDate expirationDate) {
        Recipient recipient = recipientRepository.findByEmail(recipientEmail)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipient not found"));

        SpecialOffer offer = offerRepository.findByName(offerName)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Special offer not found"));

        Voucher voucher = new Voucher();
        voucher.setRecipient(recipient);
        voucher.setSpecialOffer(offer);
        voucher.setCode(UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());
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