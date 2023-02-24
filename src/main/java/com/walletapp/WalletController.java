package com.walletapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WalletController {
@Autowired
private WalletService walletService;
//
//    {
//        "id":125567,
//            "name":"James",
//            "balance":"1200.0",
//            "eMail": "james@gmail.com",
//            "password": "James123",
//            "createdDate": "2023-02-23",
//            "fundTransferPin":1001
//    }

    //    {
//        "id":125568,
//            "name":"Maven",
//            "balance":"100.0",
//            "eMail": "Maven@gmail.com",
//            "password": "Maven1234",
//            "createdDate": "2023-02-23",
//            "fundTransferPin":1002
//    }

    @GetMapping("/home")
    public String greet(){
        return "Hello welcome to wallet app.";
    }

    @PutMapping("/addWallet")
    public WalletDto createWallet(@Valid @RequestBody WalletDto walletDto) throws WalletException {
        return walletService.registerWallet(walletDto);
    }
     //getWallet/E-Mail/{email}/Password/{password}/TD/{id}
    //  http://localhost:8080/getWallet/E-Mail/james@gmail.com/Password/James123/ID/125567
    @GetMapping("/getWallet/E-Mail/{email}/Password/{password}/ID/{id}")//if the credentials are correct then only it work
    public WalletDto getWalletById(@PathVariable Integer id,@PathVariable String email,@PathVariable String password) throws WalletException {
        return walletService.getWalletById(id,email,password);
    }
//  http://localhost:8080/updateWallet/E-Mail/Maven@gmail.com/Password/Maven123
    @PostMapping("/updateWallet/E-Mail/{email}/Password/{password}")
    public WalletDto updateWallet( @PathVariable String email,@PathVariable String password,@Valid @RequestBody WalletDto walletDto)throws WalletException{
        return walletService.updateWallet(email,password,walletDto);
    }
//  http://localhost:8080/deleteWallet/E-Mail/Maven@gmail.com/Password/Maven123/ID/125568
    //http://localhost:8080/deleteWallet/E-Mail/James@gmail.com/Password/James123/ID/125567
    @DeleteMapping("deleteWallet/E-Mail/{email}/Password/{password}/ID/{id}")
    public WalletDto deleteWalletById(@PathVariable Integer id,@PathVariable String email,@PathVariable String password) throws WalletException {
        return walletService.deleteWalletById(id,email,password);
    }
//  http://localhost:8080/addFundstoWallet/ID/125567/Amount/1500.65
    @PostMapping("/addFundstoWallet/ID/{id}/Amount/{amount}")
    public Double addFundsToWalletById(@PathVariable Integer id,@PathVariable Double amount)throws WalletException{
         return walletService.addFundsToWalletById(id,amount);
    }
    //    http://localhost:8080/withdrawFunds/ID/125567/Pin/1001/Amount/1000
@PostMapping("/withdrawFunds/ID/{id}/Pin/{pin}/Amount/{amount}")
    public Double withdrawFundsFromWalletById(@PathVariable Integer id,@PathVariable Integer pin,@PathVariable Double amount)throws WalletException {
          return walletService.withdrawFundsFromWalletById(id,amount,pin);
    }
    //http://localhost:8080/fundTransfer/ID/125567/CreditorID/125568/Pin/1001/Amount/.65
//  http://localhost:8080/fundTransfer/ID/125567/CreditorID/125568/Pin/1001/Amount/610.0
    @PostMapping("/fundTransfer/ID/{id}/CreditorID/{cid}/Pin/{pin}/Amount/{amount}")
    public Boolean fundTransfer(@PathVariable Integer id,@PathVariable Integer cid,@PathVariable Integer pin,@PathVariable Double amount)throws WalletException{
        return walletService.fundTransfer(id,cid,pin,amount);
    }
    @GetMapping("/getAllWallets")
    public List<WalletDto> getAllWallets(){
        return walletService.getAllWallets();
    }


    }
