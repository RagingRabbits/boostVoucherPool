package com.boost.voucher_api.repository;

import com.boost.voucher_api.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    Optional<Recipient> findByEmail(String email);
}