package com.example.picpaysimplificado.repositories;

import com.example.picpaysimplificado.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
