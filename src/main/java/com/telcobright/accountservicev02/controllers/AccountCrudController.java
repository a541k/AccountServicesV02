package com.telcobright.accountservicev02.controllers;

import com.telcobright.accountservicev02.dto.AccountCreationDto;
import com.telcobright.accountservicev02.entities.Account;
import com.telcobright.accountservicev02.service.AccountCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountCrudController {

    @Autowired
    AccountCrudService accountService;

    //test
    @GetMapping("/test")
    String testAccountsController(){ return "Hello from accounts crud controller"; }

    //create account
    @PostMapping("/account")
    ResponseEntity<String> createAccount(@RequestBody AccountCreationDto account){ return accountService.createAccount(account); }

    //get list of accounts for userId
    @GetMapping("/account")
    ResponseEntity<List<Account>> getAccountsListOfUser(@RequestParam(name = "userId") int userId){
        return accountService.getAccountsListOfUser(userId);
    }

    @DeleteMapping("/account/{id}")
    ResponseEntity<String> deleteAccountById(@PathVariable int id){
        return accountService.deleteAccountById(id);
    }

    @GetMapping("/account/{id}")
    ResponseEntity<Account> getAccountById(@PathVariable int id){
        return accountService.getAccountById(id);
    }

}
