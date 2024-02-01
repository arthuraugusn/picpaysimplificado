package com.example.picpaysimplificado.services;

import com.example.picpaysimplificado.domain.user.User;
import com.example.picpaysimplificado.domain.user.UserType;
import com.example.picpaysimplificado.dtos.TransactionDTO;
import com.example.picpaysimplificado.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    private AuthorizationService authService;

    @Mock
    private NotificationService notificationService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Should transaction successfully when everything is OK")
    void createTransactionCase1() throws Exception{
        User sender = new User(1, "arthur", "augusto", "99999999901", "arthur@gmail.com", "1234", new BigDecimal(50), UserType.COMMON);
        User receiver = new User(2, "joao", "augusto", "99999999902", "joao@gmail.com", "1234", new BigDecimal(50), UserType.COMMON);;

        when(userService.findUserById(1)).thenReturn(sender);
        when(userService.findUserById(2)).thenReturn(receiver);

        when(authService.autorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1, 2);
        transactionService.createTransaction(request);

        verify(transactionRepository, times(1)).save(any());

        sender.setBalance(new BigDecimal(0));
        verify(userService, times(1)).saveUser(sender);

        receiver.setBalance(new BigDecimal(20));
        verify(userService, times(1)).saveUser(receiver);

        verify(notificationService, times(1)).sendNotification(sender, "Transação realizada com sucesso");
        verify(notificationService, times(1)).sendNotification(receiver, "Transação recebida com sucesso");
    }

    @Test
    @DisplayName("Should throw Exception when Transaction is not allowed")
    void createTransactionCase2() throws Exception{
        User sender = new User(1, "arthur", "augusto", "99999999901", "arthur@gmail.com", "1234", new BigDecimal(50), UserType.COMMON);
        User receiver = new User(2, "joao", "augusto", "99999999902", "joao@gmail.com", "1234", new BigDecimal(50), UserType.COMMON);;

        when(userService.findUserById(1)).thenReturn(sender);
        when(userService.findUserById(2)).thenReturn(receiver);

        when(authService.autorizeTransaction(any(), any())).thenReturn(false);

        Exception thrown = Assertions.assertThrows(Exception.class, ()->{
            TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1, 2);
            transactionService.createTransaction(request);
        });

        Assertions.assertEquals("Transação não autorizada", thrown.getMessage());
    }

}