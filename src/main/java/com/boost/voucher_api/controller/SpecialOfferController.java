package com.boost.voucher_api.controller;

import com.boost.voucher_api.model.SpecialOffer;
import com.boost.voucher_api.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/offers")
public class SpecialOfferController {

    @Autowired
    private SpecialOfferRepository offerRepository;

    @PostMapping
    public ResponseEntity<SpecialOffer> create(@RequestBody SpecialOffer offer) {
        offerRepository.findByName(offer.getName()).ifPresent(o ->
                { throw new ResponseStatusException(HttpStatus.CONFLICT, "Offer already exists"); }
        );
        SpecialOffer saved = offerRepository.save(offer);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}