package com.telcobright.accountservicev02.controllers;


import com.telcobright.accountservicev02.service.BalanceReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reserve")
public class ReserveController {
    @Autowired
    BalanceReserveService reserveService;
    //hello
    @GetMapping("/test")
    private String testing(){
        return "reserve controller says hello";
    }


    //reserve amount
//    @PostMapping("/reserveMain")
//    private ResponseEntity<String> reserveFromMain(@RequestParam int accountNo, @RequestParam double amount){
//        return reserveService.reserveFromMain(accountNo, amount);
//    }
//
//    @PostMapping("/reserveBundle")
//    private ResponseEntity<String> reserveFromBundle(@RequestParam int accountNo, @RequestParam double minutes, @RequestParam int sms){
//        return reserveService.reserveFromBundle(accountNo, minutes, sms);
//    }

    @PostMapping("/reserveBalance")
    private  ResponseEntity<String> reserveFromAnyAccount(@RequestParam(name = "accountNo") int accountNo,
                                                          @RequestParam(name = "bdt", required = false) Double bdt,
                                                          @RequestParam(name = "minutes", defaultValue = "0") Double minutes,
                                                          @RequestParam(name = "sms", defaultValue = "0") Integer sms){
        return reserveService.reserveFromAnyAccount(accountNo, bdt, minutes, sms);
    }


    //consume/delete reserve by transactionId
    @DeleteMapping("/deleteReserve")
    private ResponseEntity<String> deleteReserve(@RequestParam String transactionId){
        return reserveService.deleteReserve(transactionId);
    }

    //return reserve
    @DeleteMapping("/returnReserve")
    private ResponseEntity<String> returnReserve(@RequestParam String transactionId){
        return reserveService.returnReserve(transactionId);
    }




}
