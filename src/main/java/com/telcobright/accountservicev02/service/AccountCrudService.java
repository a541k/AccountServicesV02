package com.telcobright.accountservicev02.service;

import com.telcobright.accountservicev02.accounttypes.AccountType;
import com.telcobright.accountservicev02.dto.AccountCreationDto;
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
public class AccountCrudService {
    
    @Autowired
    UserRepository userRepo;
    
    @Autowired
    AccountRepository accountRepo;

    //create account
    public ResponseEntity<String> createAccount(AccountCreationDto account) {
        try{
            User targetUser = validateAndGetUser(account.getUserId());

            //check if already has an account of provided type
            List<Account> accountListOfUser = targetUser.getAccountsList();
            for(Account anyAccount: accountListOfUser){
                if(anyAccount.getAccountType().equals(account.getAccountType())) throw new Exception("USER ALREADY HAS A ACCOUNT OF THAT TYPE");
            }

            Account newAccount = new Account();
            newAccount.setUser(targetUser);
            newAccount.setAccountType(account.getAccountType());
            newAccount.setCurrencyType(account.getCurrencyType());
            if(newAccount.getAccountType().equals(AccountType.MAIN)) newAccount.setMainAccountBalance((double)0);
            else if(newAccount.getAccountType().equals(AccountType.BUNDLE)) newAccount.setBundleAccountBalance(new Pair<>((double)0, 0));

            accountRepo.save(newAccount);

            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //validates given userId and password - returns matched User from database
    private User validateAndGetUser (int userId) throws Exception {
        Optional<User> optionalTargetUser = userRepo.findById(userId);
        //check if user exists
        if(optionalTargetUser.isEmpty()) throw new Exception("USER DOES NOT EXIST");

        return optionalTargetUser.get();
    }



    public ResponseEntity<List<Account>> getAccountsListOfUser(int userId) {
        try{
            User targetUser = validateAndGetUser(userId);
            return new ResponseEntity<>(targetUser.getAccountsList(), HttpStatus.FOUND);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> deleteAccountById(int id) {
        try{
            Optional<Account> optionalTargetAccount = accountRepo.findById(id);
            if(optionalTargetAccount.isEmpty()) throw new Exception("ACCOUNT DOES NOT EXIST");
            accountRepo.deleteById(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Account> getAccountById(int id) {
        try{
            Optional<Account> optionalTargetAccount = accountRepo.findById(id);
            if(optionalTargetAccount.isEmpty()) throw new Exception("ACCOUNT DOES NOT EXIST");
            return new ResponseEntity<>(optionalTargetAccount.get(), HttpStatus.FOUND);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new Account(), HttpStatus.NOT_FOUND);

        }
    }
}
