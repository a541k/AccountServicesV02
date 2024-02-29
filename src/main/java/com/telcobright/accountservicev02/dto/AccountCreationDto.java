package com.telcobright.accountservicev02.dto;

import com.telcobright.accountservicev02.accounttypes.AccountType;
import com.telcobright.accountservicev02.accounttypes.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreationDto {
    private AccountType accountType;
    private CurrencyType currencyType;
    private int userId;
}
