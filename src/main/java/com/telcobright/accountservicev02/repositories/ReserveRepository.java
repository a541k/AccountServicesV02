package com.telcobright.accountservicev02.repositories;

import com.telcobright.accountservicev02.entities.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reserve, String> {
}
