package com.boost.voucher_api.service;

import com.boost.voucher_api.dto.VoucherInfoDTO;
import com.boost.voucher_api.model.*;
import com.boost.voucher_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Service class containing business logic related to voucher creation and redemption.
 */
@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private RecipientRepository recipientRepository;

    @Autowired
    private SpecialOfferRepository offerRepository;

    /**
     * Creates a new voucher for a recipient and offer using email and offer name
     *
     * @param recipientEmail   Email of the recipient.
     * @param offerName        Name of the special offer.
     * @param expirationDate   Expiration date of the voucher.
     * @return The created Voucher object.
     * @throws ResponseStatusException if recipient or offer is not found.
     */
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

    /**
     * Redeems a voucher using its code. Validates expiration and usage status.
     *
     * @param code Unique voucher code.
     * @return The updated (redeemed) Voucher object.
     * @throws RuntimeException if voucher is not found, expired, or already used.
     */
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

    /**
     * Retrieves all valid vouchers for given recipient email.
     * @param email the email address of recipient
     * @return VoucherInfoDTO where it contains voucher info and its name
     * @throws ResponseStatusException if email address is not found
     */
    public List<VoucherInfoDTO> getValidVouchersByEmail(String email) {
        // ensure recipient exists
        recipientRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipient not found"));

        // fetch all vouchers for email
        List<Voucher> vouchers = voucherRepository.findByRecipientEmail(email);
        LocalDate today = LocalDate.now();

        return vouchers.stream()
                .filter(v -> v.getUsedAt() == null)
                .filter(v -> v.getExpirationDate() == null || !v.getExpirationDate().isBefore(today))
                .map(v -> new VoucherInfoDTO(v.getCode(), v.getSpecialOffer().getName()))
                .collect(Collectors.toList());
    }
}