package com.example.ucomandbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User>  findByPhone(String phone);

    Optional<User> findByTelegram(String telegram);

    boolean existsByTelegram(String telegram);
}