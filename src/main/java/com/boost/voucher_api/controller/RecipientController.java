package com.boost.voucher_api.controller;

import com.boost.voucher_api.model.Recipient;
import com.boost.voucher_api.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipients")
public class RecipientController {

    @Autowired
    private RecipientRepository recipientRepository;

    @PostMapping
    public ResponseEntity<Recipient> create(@RequestBody Recipient recipient) {
        return ResponseEntity.ok(recipientRepository.save(recipient));
    }
}