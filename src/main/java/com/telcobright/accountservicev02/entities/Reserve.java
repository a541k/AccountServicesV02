package com.telcobright.accountservicev02.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserve {
    @Id
    private String transactionId;

    private Double mainAccountBalance;
    private Pair<Double, Integer> bundleAccountBalance;

    @CreatedDate
    private Instant creationTime = Instant.now();
    //private Duration timeOutDuration;

    @OneToOne(optional = false)
    private Account account;

    // Method to check if the reserve has expired
//    public boolean isExpired() {
//        Instant expirationTime = creationTime.plusSeconds(90);
//        return Instant.now().isAfter(expirationTime);
//    }

}
