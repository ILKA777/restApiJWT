package ru.ilka.jwtRestApi.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.ilka.jwtRestApi.model.Role;
import ru.ilka.jwtRestApi.model.Status;
import ru.ilka.jwtRestApi.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Фабрика для создания экземпляра JwtUser на основе объекта User.
 */
public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    /**
     * Создает экземпляр JwtUser на основе объекта User.
     *
     * @param user объект User
     * @return экземпляр JwtUser
     */
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated()
        );
    }

    /**
     * Преобразует список ролей пользователя в список GrantedAuthority.
     *
     * @param userRoles список ролей пользователя
     * @return список GrantedAuthority
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
