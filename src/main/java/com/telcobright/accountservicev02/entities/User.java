package com.telcobright.accountservicev02.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId;

    String userName;
    @Column(unique = true)
    String email;
    String password;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    List<Account> accountsList;

}
