package com.example.picpaysimplificado.repositories;

import com.example.picpaysimplificado.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User>findUserByDocument(String document);
    Optional<User>findUserById(Integer id);
}
