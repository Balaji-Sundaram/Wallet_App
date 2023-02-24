package com.walletapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WalletServiceImpl implements WalletService{
    @Autowired
    private WalletRepository walletRepository;
    @Override
    public WalletDto registerWallet(WalletDto wallet) throws WalletException {
        int count =0;
        Integer n = wallet.getFundTransferPin();
        while(n != 0)
        {
            n = n / 10;
            count = count + 1;
        }
        if(count !=4 ){
            throw new WalletException("Enter 4 Digits only");
        }
            return walletRepository.createWallet(wallet);
    }

    @Override
    public WalletDto getWalletById(Integer walletId,String email,String password) throws WalletException {
        WalletDto walletDto = walletRepository.getWalletById(walletId);
        if(walletDto == null)
            throw new WalletException("Wallet Not Found");
        else {
            Integer id = walletRepository.getWalletById(walletId).getId();
            String eMail = walletRepository.getWalletById(walletId).geteMail();
            String pass = walletRepository.getWalletById(walletId).getPassword();
            if (id.equals(walletId) && eMail.equals(email) && pass.equals(password)) {
                return walletRepository.getWalletById(walletId);
            }
            throw new WalletException("You Entered Wrong Credential Check Your Id, Email, Password ");
        }
    }
    @Override
    public WalletDto updateWallet( String email,String password,WalletDto wallet) throws WalletException {
        WalletDto walletDto = walletRepository.getWalletById(wallet.getId());
        if(walletDto == null)
            throw new WalletException("Wallet Not Found");
        else {
           Integer id =  wallet.getId();
            String eMail = walletRepository.getWalletById(id).geteMail();
            String pass = walletRepository.getWalletById(id).getPassword();
            if (eMail.equals(email) && pass.equals(password)) {
                return walletRepository.updateWallet(wallet);
            }
            throw new WalletException("You Entered Wrong Credential Check Your Id, Email, Password ");
        }
        }



    @Override
    public WalletDto deleteWalletById(Integer walletId,String email,String password) throws WalletException {
        WalletDto walletDto = walletRepository.getWalletById(walletId);
        if(walletDto == null)
            throw new WalletException("Wallet Not Found");
        else {
            Integer id = walletRepository.getWalletById(walletId).getId();             //------------------>have to delete at last
            String eMail = walletRepository.getWalletById(walletId).geteMail();
            String pass = walletRepository.getWalletById(walletId).getPassword();
            if (id.equals(walletId) && eMail.equals(email) && pass.equals(password)) {
                return walletRepository.deleteWalletById(walletId);
            }
            throw new WalletException("You Entered Wrong Credential Check Your Id, Email, Password ");
        }
    }

    @Override
    public Double addFundsToWalletById(Integer walletId, Double amount) throws WalletException {
        WalletDto walletDto = walletRepository.getWalletById(walletId);
        if(walletDto == null)
            throw new WalletException("Wallet Not Found");
        else {
            return walletRepository.addFundsToWalletById(walletId, amount);
        }
    }

    @Override
    public Double withdrawFundsFromWalletById(Integer walletById, Double amount,Integer pin) throws WalletException {
        WalletDto walletDto = walletRepository.getWalletById(walletById);
        if(walletDto == null)
            throw new WalletException("Wallet Not Found");
          else {
                 if( pin.equals(walletRepository.getWalletById(walletById).getFundTransferPin())) {
                     Double balance =walletRepository.getWalletById(walletById).getBalance();
                     String toBalance= balance.toString();
                     if(walletRepository.getWalletById(walletById).getBalance()<amount)
                         throw new WalletException("Insufficient Balance and your Balance: "+toBalance );

                     return walletRepository.withdrawFundsFromWalletById(walletById,amount);
            }
                throw new WalletException("Wrong Pin or Wrong Wallet ID");
        }
    }

    @Override
    public Boolean fundTransfer(Integer fromWalletId, Integer toWalletId, Integer pin ,Double amount) throws WalletException {
        WalletDto walletDto = walletRepository.getWalletById(fromWalletId);
        WalletDto walletDto1 = walletRepository.getWalletById(toWalletId);
        if(walletDto == null  || walletDto1 == null  || fromWalletId.equals(toWalletId) )
            throw new WalletException("No Debtor or Creditor Found");
        else {
            if( pin.equals(walletRepository.getWalletById(fromWalletId).getFundTransferPin())) {
                Double balance =walletRepository.getWalletById(fromWalletId).getBalance();
                String toBalance= balance.toString();
                if(walletRepository.getWalletById(fromWalletId).getBalance()<amount)
                    throw new WalletException("Insufficient Balance and your Balance: "+toBalance);

                return walletRepository.fundTransfer(fromWalletId,toWalletId,amount);
            }
            throw new WalletException("Worng Pin or Wrong Wallet ID");
        }
    }

    @Override
    public List<WalletDto> getAllWallets() {
         return walletRepository.getAllWallets();
    }
}
