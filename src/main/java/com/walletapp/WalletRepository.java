package com.walletapp;

import java.util.List;

public interface WalletRepository {
    WalletDto createWallet(WalletDto newWallet);
    WalletDto getWalletById(Integer  walletId);
    WalletDto updateWallet(WalletDto wallet);
    WalletDto deleteWalletById(Integer walletId);
    Double addFundsToWalletById(Integer walletId,Double amount) ;
    Double withdrawFundsFromWalletById(Integer walletById,Double amount) ;
    Boolean fundTransfer(Integer fromWalletId,Integer toWalletId,Double amount) ;
    public  List<WalletDto> getAllWallets();
}
