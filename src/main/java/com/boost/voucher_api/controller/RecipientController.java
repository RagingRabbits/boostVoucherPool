package com.boost.voucher_api.controller;

import com.boost.voucher_api.model.Recipient;
import com.boost.voucher_api.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Handles creation of recipients
 * Acts as a REST controller for the /recipients API.
 */
@RestController
@RequestMapping("/recipients")
public class RecipientController {

    @Autowired
    private RecipientRepository recipientRepository;

    /**
     * Creates a recipient.
     *
     * Checks if email already exists in the database, returns 409 if so
     * Otherwise saves recipient in database and returns it
     *
     * @param recipient Recipient object consisting of name and email
     * @return HTTP 201 with recipient object, otherwise returns 409 if conflict
     */
    @PostMapping
    public ResponseEntity<Recipient> create(@RequestBody Recipient recipient) {
        //Error handling for email since email should be unique
        recipientRepository.findByEmail(recipient.getEmail()).ifPresent(r ->
                { throw new ResponseStatusException(HttpStatus.CONFLICT, "Recipient already exists"); }
        );
        Recipient saved = recipientRepository.save(recipient);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}