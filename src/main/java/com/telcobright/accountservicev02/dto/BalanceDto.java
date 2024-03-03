package com.telcobright.accountservicev02.dto;

import com.telcobright.accountservicev02.accounttypes.AccountType;
import lombok.Data;
import org.antlr.v4.runtime.misc.Pair;

@Data
public class BalanceDto {
    private int accountId;
    private AccountType accountType;
    private double mainAccountBalance;
    private Pair<Double, Integer> bundleAccountBalance;
}


