package ru.ilka.jwtRestApi.service;

import ru.ilka.jwtRestApi.model.User;

import java.util.List;

/**
 * UserService предоставляет операции для работы с пользователями.
 */
public interface UserService {

    /**
     * Регистрирует нового пользователя.
     *
     * @param user данные пользователя
     * @return зарегистрированный пользователь
     */
    User register(User user);

    /**
     * Ищет пользователя по его имени пользователя.
     *
     * @param username имя пользователя
     * @return найденный пользователь или null, если пользователь не найден
     */
    User findByUsername(String username);

    /**
     * Ищет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь или null, если пользователь не найден
     */
    User findById(Long id);

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     */
    void delete(Long id);

    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей
     */
    List<User> getAll();
}
