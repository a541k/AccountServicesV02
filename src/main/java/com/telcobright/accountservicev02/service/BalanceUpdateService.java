package com.telcobright.accountservicev02.service;

import com.telcobright.accountservicev02.accounttypes.AccountType;
import com.telcobright.accountservicev02.dto.BalanceDto;
import com.telcobright.accountservicev02.entities.Account;
import com.telcobright.accountservicev02.entities.User;
import com.telcobright.accountservicev02.repositories.AccountRepository;
import com.telcobright.accountservicev02.repositories.UserRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BalanceUpdateService {
    @Autowired
    AccountRepository accountRepo;

    @Autowired
    UserRepository userRepo;

//    public ResponseEntity<String> topUpMainAccount(int accountId, double amount) {
//        try{
//            //gets account if exists
//            Account targetAccount = checkAccountExistence(accountId);
//
//            targetAccount.setMainAccountBalance( targetAccount.getMainAccountBalance() + amount );
//
//            accountRepo.save(targetAccount);
//
//            return new ResponseEntity<>("Successful", HttpStatus.OK);
//
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//
//    public ResponseEntity<String> topUpBundleAccount(int accountId, double minutes, int smsCount) {
//        try{
//            //gets account if exists
//            Account targetAccount = checkAccountExistence(accountId);
//            //System.out.println(targetAccount.getBundleAccountBalance());
//
//            Pair<Double, Integer> targetBundleAccount = targetAccount.getBundleAccountBalance();
//
//            double newMinutesBalance = targetBundleAccount.a + minutes;
//            int newSmsBalance = targetBundleAccount.b + smsCount;
//
//            targetAccount.setBundleAccountBalance(new Pair<>(newMinutesBalance, newSmsBalance));
//
//            accountRepo.save(targetAccount);
//
//            return new ResponseEntity<>("Successful", HttpStatus.OK);
//
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    public ResponseEntity<String> topUpAnyAccount(int accountId, Double bdt, Double minutes, Integer sms) {
        try{
            //gets account if exists
            Account targetAccount = checkAccountExistence(accountId);

            //top up main
            if(targetAccount.getAccountType().equals(AccountType.MAIN)){
                targetAccount.setMainAccountBalance( targetAccount.getMainAccountBalance() + bdt );
            }
            //top up bundle
            else if (targetAccount.getAccountType().equals(AccountType.BUNDLE)) {
                Pair<Double, Integer> targetBundleAccount = targetAccount.getBundleAccountBalance();
                double newMinutesBalance = targetBundleAccount.a + minutes;
                int newSmsBalance = targetBundleAccount.b + sms;
                targetAccount.setBundleAccountBalance(new Pair<>(newMinutesBalance, newSmsBalance));
            }

            accountRepo.save(targetAccount);

            return new ResponseEntity<>("Successful", HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<List<BalanceDto>> checkAccountBalance(int userId) {
        try{
            User user = checkUserExistence(userId);

            List<BalanceDto> balanceDtoList = getBalanceDtoList(user.getAccountsList());

            return new ResponseEntity<>(balanceDtoList, HttpStatus.FOUND);

        }
        catch (Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }

    }


    //---------------------------------------------------------------------------------------------

    private List<BalanceDto> getBalanceDtoList(List<Account> accountsList) {
        List<BalanceDto> balanceDtoList = new ArrayList<>();
        for(Account account: accountsList){
            BalanceDto tempBalanceDto = new BalanceDto();

            tempBalanceDto.setAccountId(account.getAccountId());

            if(account.getAccountType().equals(AccountType.MAIN)){
                tempBalanceDto.setAccountType(AccountType.MAIN);
                tempBalanceDto.setMainAccountBalance(account.getMainAccountBalance());

            } else if (account.getAccountType().equals(AccountType.BUNDLE)) {
                tempBalanceDto.setAccountType(AccountType.BUNDLE);
                tempBalanceDto.setBundleAccountBalance(account.getBundleAccountBalance());
            }

            balanceDtoList.add(tempBalanceDto);
        }
        return balanceDtoList;
    }


    private User checkUserExistence(int userId) throws Exception{
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isEmpty()) throw new Exception("USER DOES NOT EXIST!");
        return optionalUser.get();
    }


    //returns account if exists
    private Account checkAccountExistence(int accountId) throws Exception{
        Optional<Account> optionalAccount = accountRepo.findById(accountId);
        if(optionalAccount.isEmpty()) throw new Exception("ACCOUNT DOES NOT EXIST!");
        return optionalAccount.get();
    }



}
