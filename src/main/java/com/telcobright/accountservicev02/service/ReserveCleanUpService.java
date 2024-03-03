package com.telcobright.accountservicev02.service;

import com.telcobright.accountservicev02.entities.Reserve;
import com.telcobright.accountservicev02.repositories.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class ReserveCleanUpService {
    @Autowired
    ReserveRepository reserveRepo;

    @Autowired
    BalanceReserveService reserveService;


    @Scheduled(fixedRate = 30000)
    public void cleanUpExpiredReserve(){
        System.out.println("Cleaning up Expired reserves");
        Instant expirationTime = Instant.now().minusSeconds(90);
        List<Reserve> expiredReserves = reserveRepo.findAllByCreationTimeBefore(expirationTime);

        for(Reserve reserve: expiredReserves){
            reserveService.returnReserve(reserve.getTransactionId());
        }
        //reserveRepo.deleteAll(expiredReserves);
    }
}
    `