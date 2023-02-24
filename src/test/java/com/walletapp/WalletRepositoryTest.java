package com.walletapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class WalletRepositoryTest {
           @Autowired
    private WalletRepository walletRepository;
    @BeforeEach
    public void init()   {
        LocalDate da = LocalDate.now();
        walletRepository.createWallet(new WalletDto(125567,"james",1200.0,"james@gmail.com","James123",da,1001));
    }

    @Test
    public void getWallet() {
        assertEquals("james",walletRepository.getWalletById(125567).getName());
    }
    @Test
    public void updateWallet() {
        LocalDate da = LocalDate.now();
        WalletDto walletDto=new WalletDto(125567,"Maven",1200.0,"Maven@gmail.com","Maven123",da,1002);
        walletRepository.updateWallet(  walletDto);  //first update
        assertEquals("Maven",walletRepository.getWalletById(125567).getName());  //check with new data
    }

    @Test
    public void addFundsToWalletById()   {
        walletRepository.addFundsToWalletById(125567,300.0);
        assertEquals(1500.0, walletRepository.getWalletById(125567 ).getBalance());
    }

    @Test
    public void withdrawFundsFromWalletById() {
        walletRepository.withdrawFundsFromWalletById(125567,500.0 );
        assertEquals(700.0,walletRepository.getWalletById(125567).getBalance());
    }


    @Test
    public void withdrawFundsFromWalletIdError(){  //this one rest will do the null pointer
        assertThrows(NullPointerException.class,()->walletRepository.withdrawFundsFromWalletById(125564,500.0));
    }

    @Test
    public void fundTransfer() {
        LocalDate da = LocalDate.now();
        WalletDto walletDto=new WalletDto(125568,"Maven",1200.0,"Maven@gmail.com","Maven123",da,1002);
        walletRepository.createWallet(walletDto);
        walletRepository.fundTransfer(125567,125568, 1000.0);
        assertEquals(200.0,walletRepository.getWalletById(125567 ).getBalance());
    }

}
