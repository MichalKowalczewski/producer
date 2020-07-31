package com.example.producer.repository;

import com.example.producer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findFirstByOrderByIdAsc();

    User findFirstByOrderByIdDesc();
}
