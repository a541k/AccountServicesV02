package com.telcobright.accountservicev02.repositories;

import com.telcobright.accountservicev02.entities.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, String> {
    List<Reserve> findAllByCreationTimeBefore(Instant expirationTime);
}
