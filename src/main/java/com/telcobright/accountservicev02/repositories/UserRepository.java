package com.telcobright.accountservicev02.repositories;

import com.telcobright.accountservicev02.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
}
