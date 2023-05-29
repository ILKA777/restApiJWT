package ru.ilka.jwtRestApi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilka.jwtRestApi.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);

    List<User> findAll();
}