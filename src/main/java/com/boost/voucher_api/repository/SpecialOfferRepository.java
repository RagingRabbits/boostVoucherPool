package com.boost.voucher_api.repository;

import com.boost.voucher_api.model.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Long> {
    Optional<SpecialOffer> findByName(String name);
}