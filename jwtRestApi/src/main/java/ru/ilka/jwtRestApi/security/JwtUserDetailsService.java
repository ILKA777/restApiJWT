package ru.ilka.jwtRestApi.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ilka.jwtRestApi.model.User;
import ru.ilka.jwtRestApi.security.jwt.JwtUser;
import ru.ilka.jwtRestApi.security.jwt.JwtUserFactory;
import ru.ilka.jwtRestApi.service.UserService;

/**
 * Сервис, реализующий интерфейс UserDetailsService для аутентификации пользователей с использованием JWT.
 */
@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Загружает пользовательские данные по имени пользователя для аутентификации.
     *
     * @param username имя пользователя
     * @return объект UserDetails, представляющий пользовательские данные
     * @throws UsernameNotFoundException если пользователь с указанным именем не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}
