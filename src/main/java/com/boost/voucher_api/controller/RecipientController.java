package com.boost.voucher_api.controller;

import com.boost.voucher_api.model.Recipient;
import com.boost.voucher_api.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/recipients")
public class RecipientController {

    @Autowired
    private RecipientRepository recipientRepository;

    @PostMapping
    public ResponseEntity<Recipient> create(@RequestBody Recipient recipient) {
        recipientRepository.findByEmail(recipient.getEmail()).ifPresent(r ->
                { throw new ResponseStatusException(HttpStatus.CONFLICT, "Recipient already exists"); }
        );
        Recipient saved = recipientRepository.save(recipient);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}