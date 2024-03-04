package com.telcobright.accountservicev02.service;

import com.telcobright.accountservicev02.accounttypes.AccountType;
import com.telcobright.accountservicev02.entities.Account;
import com.telcobright.accountservicev02.entities.Reserve;
import com.telcobright.accountservicev02.repositories.AccountRepository;
import com.telcobright.accountservicev02.repositories.ReserveRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class BalanceReserveService {
    @Autowired
    AccountRepository accountRepo;

    @Autowired
    ReserveRepository reserveRepo;
    public ResponseEntity<String> reserveFromMain(int accountNo, double amount) {
        try{
            Account targetAccount = checkAccountExistence(accountNo);

            if(!targetAccount.getAccountType().equals(AccountType.MAIN)) throw new Exception("MUST BE A MAIN ACCOUNT");

            if(targetAccount.getMainAccountBalance() < amount) throw new Exception("INSUFFICIENT BALANCE");

            targetAccount.setMainAccountBalance( targetAccount.getMainAccountBalance() - amount);

            Reserve reserve = new Reserve();
            reserve.setMainAccountBalance(amount);
            reserve.setTransactionId(getRandomString());
            reserve.setAccount(targetAccount);

            reserveRepo.save(reserve);
            accountRepo.save(targetAccount);

            return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> reserveFromBundle(int accountNo, double minutes, int sms) {
        try{
            Account targetAccount = checkAccountExistence(accountNo);
            if(!targetAccount.getAccountType().equals(AccountType.BUNDLE)) throw new Exception("MUST BE A BUNDLE ACCOUNT");

            Pair<Double, Integer> bundleBalance = targetAccount.getBundleAccountBalance();
            if(bundleBalance.a < minutes || bundleBalance.b < sms) throw new Exception("INSUFFICIENT BALANCE");

            Pair<Double, Integer> newBundleBalance = new Pair<>(bundleBalance.a - minutes, bundleBalance.b - sms);

            targetAccount.setBundleAccountBalance(newBundleBalance);
            accountRepo.save(targetAccount);

            Reserve reserve = new Reserve();
            reserve.setBundleAccountBalance(newBundleBalance);
            reserve.setTransactionId(getRandomString());
            reserve.setAccount(targetAccount);

            reserveRepo.save(reserve);

            return new ResponseEntity<>("RESERVED", HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> reserveFromAnyAccount(Integer accountNo, Double bdt, Double minutes, Integer sms) {
        try{
            Account targetAccount = checkAccountExistence(accountNo);

            Reserve reserve = new Reserve();

            //reserve main account balance
            if(targetAccount.getAccountType().equals(AccountType.MAIN)){
                if(targetAccount.getMainAccountBalance() < bdt) throw new Exception("INSUFFICIENT BALANCE");

                targetAccount.setMainAccountBalance( targetAccount.getMainAccountBalance() - bdt);
                reserve.setMainAccountBalance(bdt);
            }

            //reserve bundle account balance
            else if (targetAccount.getAccountType().equals(AccountType.BUNDLE)) {
                Pair<Double, Integer> bundleBalance = targetAccount.getBundleAccountBalance();
                if(bundleBalance.a < minutes || bundleBalance.b < sms) throw new Exception("INSUFFICIENT BALANCE");

                Pair<Double, Integer> newBundleBalance = new Pair<>(bundleBalance.a - minutes, bundleBalance.b - sms);

                targetAccount.setBundleAccountBalance(newBundleBalance);
                reserve.setBundleAccountBalance(new Pair<>(minutes, sms));

            }

            reserve.setTransactionId(getRandomString());
            reserve.setAccount(targetAccount);
            reserveRepo.save(reserve);
            accountRepo.save(targetAccount);

            return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    public ResponseEntity<String> deleteReserve(String transactionId) {
        try {
            Reserve targetReserve = checkReserveExistence(transactionId);
            reserveRepo.deleteById(transactionId);
            return new ResponseEntity<>("SUCCESSFULLY DELETED", HttpStatus.OK);
        }
        catch (Exception e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<String> returnReserve(String transactionId) {
        try {
            Reserve targetReserve = checkReserveExistence(transactionId);

            Account targetAccount = targetReserve.getAccount();
            if(targetAccount.getAccountType().equals(AccountType.MAIN)){
                targetAccount.setMainAccountBalance( targetAccount.getMainAccountBalance() + targetReserve.getMainAccountBalance() );
            }
            else if(targetAccount.getAccountType().equals(AccountType.BUNDLE)){
                double refundMinutes = targetReserve.getBundleAccountBalance().a;
                int refundSmsCount = targetReserve.getBundleAccountBalance().b;

                targetAccount.setBundleAccountBalance(new Pair<>(targetAccount.getBundleAccountBalance().a + refundMinutes, targetAccount.getBundleAccountBalance().b + refundSmsCount));
            }

            accountRepo.save(targetAccount);
            reserveRepo.deleteById(transactionId);

            return new ResponseEntity<>("RETURNED", HttpStatus.OK);
        }
        catch (Exception e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }






    private Reserve checkReserveExistence(String transactionId) throws Exception{
        Optional<Reserve> optionalTargetReserve = reserveRepo.findById(transactionId);
        if(optionalTargetReserve.isEmpty()) throw new Exception("TRANSACTION ID NOT FOUND");
        return optionalTargetReserve.get();
    }


    private String getRandomString() {
        Random rand = new Random();
        StringBuilder str = new StringBuilder();
        for(int i=0; i<10; i++){
            int num = rand.nextInt(26);
            str.append((char)('a' + num));
        }
        return str.toString();
    }


    //returns account if exists
    private Account checkAccountExistence(int accountId) throws Exception{
        Optional<Account> optionalAccount = accountRepo.findById(accountId);
        if(optionalAccount.isEmpty()) throw new Exception("ACCOUNT DOES NOT EXIST!");
        return optionalAccount.get();
    }



}
