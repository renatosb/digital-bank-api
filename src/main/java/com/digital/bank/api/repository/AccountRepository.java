package com.digital.bank.api.repository;

import com.digital.bank.api.entity.Account;
import com.digital.bank.api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNumber(UUID accountNumber);

    @Query("SELECT t FROM Transaction t WHERE t.sourceAccount.number = :accountNumber " +
            "OR t.destinationAccount.number = :accountNumber")
    List<Transaction> findTransactionsByAccountNumber(UUID accountNumber);
}
