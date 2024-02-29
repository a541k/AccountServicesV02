package com.telcobright.accountservicev02.repositories;

import com.telcobright.accountservicev02.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
