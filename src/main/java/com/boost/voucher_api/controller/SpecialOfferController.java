package com.boost.voucher_api.controller;

import com.boost.voucher_api.model.SpecialOffer;
import com.boost.voucher_api.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offers")
public class SpecialOfferController {

    @Autowired
    private SpecialOfferRepository offerRepository;

    @PostMapping
    public ResponseEntity<SpecialOffer> create(@RequestBody SpecialOffer offer) {
        return ResponseEntity.ok(offerRepository.save(offer));
    }
}