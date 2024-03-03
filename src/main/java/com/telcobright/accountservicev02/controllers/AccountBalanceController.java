package com.telcobright.accountservicev02.controllers;

import com.telcobright.accountservicev02.dto.BalanceDto;
import com.telcobright.accountservicev02.service.BalanceUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/balance")
public class AccountBalanceController {

    @Autowired
    BalanceUpdateService balanceService;
    @GetMapping("/test")
    String testing(){
        return "hello from balance";
    }

    //topup account
    @PutMapping("/topup/MAIN")
    ResponseEntity<String> topUpMainAccount(@RequestParam int accountId, @RequestParam double amount){
        return balanceService.topUpMainAccount(accountId, amount);
    }

    @PutMapping("/topup/BUNDLE")
    ResponseEntity<String> topUpBundleAccount(@RequestParam int accountId, @RequestParam double minutes, @RequestParam int smsCount){
        return balanceService.topUpBundleAccount(accountId, minutes, smsCount);
    }

    @GetMapping("/checkBalance")
    ResponseEntity<List<BalanceDto>> checkAccountBalance(@RequestParam int userId){
        return balanceService.checkAccountBalance(userId);
    }
//


}
