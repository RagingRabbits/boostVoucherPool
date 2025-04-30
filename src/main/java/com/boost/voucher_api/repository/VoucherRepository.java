package com.boost.voucher_api.repository;

import com.boost.voucher_api.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Optional<Voucher> findByCode(String code);

    /**
     * Retrieves all vouchers for a given recipient email by joining with the Recipient entity.
     * This allows us to look up vouchers using the recipient's email
     */
    @Query("SELECT v FROM Voucher v JOIN v.recipient r WHERE r.email = :email")
    List<Voucher> findByRecipientEmail(@Param("email") String email);
}
