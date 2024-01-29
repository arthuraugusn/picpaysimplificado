package com.example.picpaysimplificado.controllers;

import com.example.picpaysimplificado.domain.transaction.Transaction;
import com.example.picpaysimplificado.dtos.TransactionDTO;
import com.example.picpaysimplificado.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO)throws Exception{

        Transaction transaction = this.transactionService.createTransaction(transactionDTO);

        return new ResponseEntity<>(transaction, HttpStatus.OK);

    }


}
