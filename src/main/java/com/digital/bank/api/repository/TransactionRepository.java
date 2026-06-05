package com.digital.bank.api.repository;

import com.digital.bank.api.entity.Account;
import com.digital.bank.api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
