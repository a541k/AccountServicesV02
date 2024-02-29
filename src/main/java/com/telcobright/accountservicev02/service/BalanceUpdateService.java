package com.telcobright.accountservicev02.service;

import com.telcobright.accountservicev02.entities.Account;
import com.telcobright.accountservicev02.repositories.AccountRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceUpdateService {
    @Autowired
    AccountRepository accountRepo;
    public ResponseEntity<String> topUpMainAccount(int accountId, double amount) {
        try{
            //gets account if exists
            Account targetAccount = checkAccountExistence(accountId);

            targetAccount.setMainAccountBalance( targetAccount.getMainAccountBalance() + amount );

            accountRepo.save(targetAccount);

            return new ResponseEntity<>("Successful", HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    public ResponseEntity<String> topUpBundleAccount(int accountId, double minutes, int smsCount) {
        try{
            //gets account if exists
            Account targetAccount = checkAccountExistence(accountId);
            System.out.println(targetAccount.getBundleAccountBalance());

            Pair<Double, Integer> targetBundleAccount = targetAccount.getBundleAccountBalance();

            double newMinutesBalance = targetBundleAccount.a + minutes;
            int newSmsBalance = targetBundleAccount.b + smsCount;

            targetAccount.setBundleAccountBalance(new Pair<>(newMinutesBalance, newSmsBalance));

            accountRepo.save(targetAccount);

            return new ResponseEntity<>("Successful", HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //returns account if exists
    private Account checkAccountExistence(int accountId) throws Exception{
        Optional<Account> optionalAccount = accountRepo.findById(accountId);
        if(optionalAccount.isEmpty()) throw new Exception("ACCOUNT DOES NOT EXIST!");
        return optionalAccount.get();
    }
}
