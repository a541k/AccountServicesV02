package com.telcobright.accountservicev02.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telcobright.accountservicev02.accounttypes.AccountType;
import com.telcobright.accountservicev02.accounttypes.CurrencyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.antlr.v4.runtime.misc.Pair;


@Entity
@Data
//@AllArgsConstructor
//@NoArgsConstructor
@ToString(exclude = "user")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    private AccountType accountType;
    private CurrencyType currencyType;

    private Double mainAccountBalance;
    private Pair<Double, Integer> bundleAccountBalance;



    @ManyToOne(optional = false)
    @JsonIgnore
    User user;

}
