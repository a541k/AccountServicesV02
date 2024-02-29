package com.telcobright.accountservicev02.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserve {
    @Id
    private String transactionId;

    private Double mainAccountBalance;
    private Pair<Double, Integer> bundleAccountBalance;

    @OneToOne(optional = false)
    private Account account;

}
