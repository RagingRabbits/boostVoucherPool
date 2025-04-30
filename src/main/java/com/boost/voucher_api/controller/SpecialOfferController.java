package com.boost.voucher_api.controller;

import com.boost.voucher_api.model.SpecialOffer;
import com.boost.voucher_api.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Handles creation of special offers.
 * Acts as a REST controller for the /offers API.
 */
@RestController
@RequestMapping("/offers")
public class SpecialOfferController {

    @Autowired
    private SpecialOfferRepository offerRepository;

    /**
     * Endpoint to create a new special offer.
     *
     * Checks if an offer with the same name already exists in the database.
     * If so, returns a 409 Conflict status. Otherwise, saves the new offer and returns it.
     *
     * @param offer SpecialOffer object consisting of name and percentageDiscount.
     * @return HTTP 201 Created with the saved SpecialOffer object, otherwise return 409 if conflict.
     */
    @PostMapping
    public ResponseEntity<SpecialOffer> create(@RequestBody SpecialOffer offer) {
        offerRepository.findByName(offer.getName()).ifPresent(o ->
                { throw new ResponseStatusException(HttpStatus.CONFLICT, "Offer already exists"); }
        );
        SpecialOffer saved = offerRepository.save(offer);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}