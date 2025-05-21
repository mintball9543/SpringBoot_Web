package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findById(Long id);

    boolean existsByEmail(String email);

    User save(User user);

    User update(User user);

    void deleteById(Long id);

    List<User> searchUserByName(String name);

    Integer updateEmail(Long id, String email);
}
