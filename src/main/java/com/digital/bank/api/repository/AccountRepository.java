package com.digital.bank.api.repository;

import com.digital.bank.api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNumber(UUID accountNumber);
}
