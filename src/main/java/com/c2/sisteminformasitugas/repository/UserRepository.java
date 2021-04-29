package com.c2.sisteminformasitugas.repository;

import com.c2.sisteminformasitugas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
