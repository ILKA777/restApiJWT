package ru.ilka.jwtRestApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilka.jwtRestApi.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}